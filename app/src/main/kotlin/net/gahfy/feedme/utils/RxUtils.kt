package net.gahfy.feedme.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Shortcut of Schedulers.io()
 * @return the result of Schedulers.io()
 * @see Schedulers.io()
 */
fun ioThread(): Scheduler {
    return Schedulers.io()
}

/**
 * Shortcut of AndroidSchedulers.mainThread()
 * @return the result of AndroidSchedulers.mainThread()
 * @see AndroidSchedulers.mainThread()
 */
fun androidThread(): Scheduler {
    return AndroidSchedulers.mainThread()
}