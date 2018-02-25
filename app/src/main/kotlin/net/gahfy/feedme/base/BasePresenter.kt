package net.gahfy.feedme.base

import net.gahfy.feedme.injection.component.DaggerPresenterInjector
import net.gahfy.feedme.injection.component.PresenterInjector
import net.gahfy.feedme.injection.module.ContextModule
import net.gahfy.feedme.injection.module.NetworkModule
import net.gahfy.presenterinjector.annotations.BasePresenter

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @property injector The injector used to inject required dependencies
 * @constructor Injects the required dependencies
 */
@BasePresenter
abstract class BasePresenter<out V : BaseView>(protected val view: V) {
    private val injector: PresenterInjector = DaggerPresenterInjector.builder().contextModule(ContextModule(view)).networkModule(NetworkModule).build()

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed(){}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        makeInjection(injector, this)
    }
}