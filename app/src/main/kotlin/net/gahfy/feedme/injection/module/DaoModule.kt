package net.gahfy.feedme.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import net.gahfy.feedme.model.FeedMeDatabase
import net.gahfy.feedme.model.PostDao
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about Dao.
 */
@Module(includes = [(ContextModule::class)])
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object DaoModule {
    /** The name of the database */
    private const val DATABASE_NAME = "FeedMe"

    /**
     * Provides the Room Database of the application.
     * @param application The application currently running
     * @return the Room Database of the application
     */
    @Provides
    @Singleton
    internal fun provideFeedMeDatabase(application: Application): FeedMeDatabase {
        return Room.databaseBuilder(application, FeedMeDatabase::class.java, DATABASE_NAME).build()
    }

    /**
     * Provides the Post Dao.
     * @param db the Database of the application
     * @return the Post Dao
     */
    @Provides
    @Singleton
    internal fun providePostDao(db: FeedMeDatabase): PostDao {
        return db.postDao()
    }
}