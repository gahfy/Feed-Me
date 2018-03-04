package net.gahfy.feedme.ui.post

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
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
 * @property context the context in which the application is running
 * @property subscription the subscription to the API call
 * @property mutablePostList the live data list of posts
 */
class PostPresenter(postView: PostView) : BasePresenter<PostView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var postDao: PostDao

    private var subscription: Disposable? = null

    private var mutablePostList: MutableLiveData<List<Post>> = MutableLiveData()

    override fun onViewCreated() {
        val postListObserver = Observer<List<Post>> { postList ->
            if (postList != null) {
                view.updatePosts(postList)
            }
        }

        mutablePostList.observe(view, postListObserver)
        getPostsFromDb()
    }

    /**
     * Returns the list of posts from database. If it is not available in the database, then load it
     * from the JSONPlaceholder API then insert it in database.
     */
    private fun getPostsFromDb() {
        view.showLoading()
        Observable.fromCallable { postDao.all }
                .flatMap { postList -> if (postList.isNotEmpty()) Observable.just(postList) else loadPostsObservable() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { postList -> mutablePostList.value = postList },
                        { view.showError(context.getString(R.string.unknown_error)) }
                )
    }

    /**
     * Returns the Observable to get list of Posts from JSONPlaceholder API and insert them in
     * database.
     */
    private fun loadPostsObservable(): Observable<List<Post>> {
        return postApi.getPosts()
                .flatMap { postList -> postDao.insertAll(*postList.toTypedArray());Observable.just(postList) }
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}