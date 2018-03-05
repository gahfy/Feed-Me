package net.gahfy.feedme.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Interface which provide methods for accessing Database Access Objects
 */
@Database(entities = [(Post::class)], version = 1)
abstract class FeedMeDatabase : RoomDatabase() {
    /**
     * Returns the Database Access Object to deal with posts.
     * @return the Database Access Object to deal with posts
     */
    abstract fun postDao(): PostDao
}