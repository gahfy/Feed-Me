package net.gahfy.feedme.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * The Dao to perform requests on the posts on Database.
 * @property all the list of all posts in dabase
 */
@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: List<Post>

    /**
     * Inserts the specified Posts in database.
     * @param posts the list of Posts to insert in database
     */
    @Insert
    fun insertAll(vararg posts: Post)
}