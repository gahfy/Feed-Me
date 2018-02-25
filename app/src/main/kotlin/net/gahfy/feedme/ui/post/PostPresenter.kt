package net.gahfy.feedme.ui.post

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.gahfy.feedme.R
import net.gahfy.feedme.base.BasePresenter
import net.gahfy.feedme.network.PostApi
import net.gahfy.presenterinjector.annotations.FinalPresenter
import javax.inject.Inject

/**
 * The FinalPresenter that will present the Post view.
 * @param postView the Post view to be presented by the presenter
 * @property postApi the API interface implementation
 * @property context the context in which the application is running
 * @property subscription the subscription to the API call
 */
@FinalPresenter
class PostPresenter(postView: PostView) : BasePresenter<PostView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    @Inject
    lateinit var context: Context

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
        subscription = postApi
                .getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { postList -> view.updatePosts(postList) },
                        { view.showError(context.getString(R.string.unknown_error)) }
                )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}