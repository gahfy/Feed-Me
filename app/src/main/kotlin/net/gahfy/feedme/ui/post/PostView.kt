package net.gahfy.feedme.ui.post

import net.gahfy.feedme.base.BaseView
import net.gahfy.feedme.model.Post

/**
 * Interface providing required method for a view displaying posts
 */
interface PostView : BaseView {
    /**
     * Updates the previous posts by the specified ones
     * @param posts the list of posts that will replace existing ones
     */
    fun updatePosts(posts: List<Post>)

    /**
     * Displays an error in the view
     * @param error the error to display in the view
     */
    fun showError(error: String)

    /**
     * Displays the loading indicator of the view
     */
    fun showLoading()

    /**
     * Hides the loading indicator of the view
     */
    fun hideLoading()
}