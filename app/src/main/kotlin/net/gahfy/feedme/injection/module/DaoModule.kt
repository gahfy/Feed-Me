package net.gahfy.feedme.injection.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import net.gahfy.feedme.injection.module.DaoModule.DATABASE_NAME
import net.gahfy.feedme.model.FeedMeDatabase
import net.gahfy.feedme.model.PostDao

/**
 * Module which provides all dependecies about databases
 * @property DATABASE_NAME The name of the database of the application
 */
@Module(includes = [(ContextModule::class)])
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object DaoModule {
    private const val DATABASE_NAME = "FeedMe"

    /**
     * Returns the database of the application.
     * @param context Context in which the application is running
     * @return the database of the application
     */
    @Provides
    @JvmStatic
    internal fun provideFeedMeDatabase(context: Context): FeedMeDatabase {
        return Room.databaseBuilder(context.applicationContext, FeedMeDatabase::class.java, DATABASE_NAME).build()
    }

    /**
     * Returns the Dao of Posts.
     * @param db the database of the application
     * @return the Dao of Posts
     */
    @Provides
    @JvmStatic
    internal fun providePostDao(db: FeedMeDatabase): PostDao {
        return db.postDao()
    }
}