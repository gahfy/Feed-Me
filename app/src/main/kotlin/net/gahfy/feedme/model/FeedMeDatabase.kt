package net.gahfy.feedme.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * The Room Database of the application
 */
@Database(entities = [(Post::class)], version = 1)
abstract class FeedMeDatabase : RoomDatabase() {
    /**
     * Returns the Post Dao.
     * @return the Post Dao
     */
    abstract fun postDao(): PostDao
}