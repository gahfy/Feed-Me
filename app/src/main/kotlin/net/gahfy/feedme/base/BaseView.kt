package net.gahfy.feedme.base

import android.content.Context
import net.gahfy.presenterinjector.annotations.BaseView

/**
 * Base view any view must implement.
 */
@BaseView
interface BaseView {
    /**
     * Returns the Context in which the application is running.
     * @return the Context in which the application is running
     */
    fun getContext(): Context
}