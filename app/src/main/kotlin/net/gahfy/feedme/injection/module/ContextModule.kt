package net.gahfy.feedme.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import net.gahfy.feedme.base.BaseView

/**
 * Module which provides all required dependencies about Context
 * @property baseView the BaseView used to provides required dependencies
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
class ContextModule constructor(private val baseView: BaseView) {
    /**
     * Provides the Context
     * @return the Context to be provided
     */
    @Provides
    internal fun provideContext(): Context {
        return baseView.getContext()
    }

    /**
     * Provides the Application Context
     * @param context Context in which the application is running
     * @return the Application Context to be provided
     */
    @Provides
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }
}