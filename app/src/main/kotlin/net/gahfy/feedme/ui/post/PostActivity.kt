package net.gahfy.feedme.ui.post

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import net.gahfy.feedme.R
import net.gahfy.feedme.base.BaseActivity
import net.gahfy.feedme.databinding.ActivityPostBinding
import net.gahfy.feedme.model.Post

/**
 * Activity displaying the list of posts
 */
class PostActivity : BaseActivity<PostPresenter>(), PostView {
    /**
     * DataBinding instance
     */
    private lateinit var binding: ActivityPostBinding

    /**
     * The adapter for the list of posts
     */
    private val postsAdapter = PostAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding.adapter = postsAdapter
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun updatePosts(posts: List<Post>) {
        postsAdapter.updatePosts(posts)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun instantiatePresenter(): PostPresenter {
        return PostPresenter(this)
    }
}