package net.gahfy.feedme.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * The database object in charge of inserting Post objects and retrieving them from Database.
 * @property all the list of all Posts in database
 */
@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: List<Post>

    /**
     * Inserts specified posts in database.
     * @param posts all the posts to insert in database
     */
    @Insert
    fun insertAll(vararg posts: Post)
}