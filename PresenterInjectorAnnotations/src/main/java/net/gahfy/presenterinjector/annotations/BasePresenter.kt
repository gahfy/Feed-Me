package net.gahfy.presenterinjector.annotations

/**
 * Annotates the base presenter in which the makeinjection() method will be called.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BasePresenter