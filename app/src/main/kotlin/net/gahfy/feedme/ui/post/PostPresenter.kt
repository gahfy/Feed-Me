package net.gahfy.feedme.ui.post

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.gahfy.feedme.R
import net.gahfy.feedme.base.BasePresenter
import net.gahfy.feedme.model.Post
import net.gahfy.feedme.model.PostDao
import net.gahfy.feedme.network.PostApi
import javax.inject.Inject

/**
 * The Presenter that will present the Post view.
 * @param postView the Post view to be presented by the presenter
 * @property postApi the API interface implementation
 * @property subscription the subscription to the API call
 */
class PostPresenter(postView: PostView) : BasePresenter<PostView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    @Inject
    lateinit var postDao: PostDao

    private var subscription: Disposable? = null

    override fun onViewCreated() {
        loadPosts()
    }

    /**
     * Loads the posts from the API and presents them in the view when retrieved, or showss error if
     * any.
     */
    fun loadPosts() {
        view.showLoading()
        subscription = Observable.fromCallable({ postDao.all })
                .flatMap { postList -> if (postList.isNotEmpty()) Observable.just(postList) else saveApiPostsInDatabase() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { postList -> view.updatePosts(postList) },
                        { view.showError(R.string.unknown_error) }
                )
    }

    /**
     * Load the posts from the API and store them in the database.
     * @return an Observable for the list of Post retrieved from API
     */
    private fun saveApiPostsInDatabase(): Observable<List<Post>> {
        return postApi.getPosts()
                .flatMap { postList -> Observable.fromCallable({ postDao.insertAll(*postList.toTypedArray());postList }) }
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}