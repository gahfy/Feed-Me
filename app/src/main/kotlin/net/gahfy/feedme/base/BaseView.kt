package net.gahfy.feedme.base

import android.arch.lifecycle.LifecycleOwner
import android.content.Context

/**
 * Base view any view must implement.
 */
interface BaseView : LifecycleOwner {
    /**
     * Returns the Context in which the application is running.
     * @return the Context in which the application is running
     */
    fun getContext(): Context
}