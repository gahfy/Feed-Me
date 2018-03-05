package net.gahfy.feedme.injection.component

import dagger.BindsInstance
import dagger.Component
import net.gahfy.feedme.base.BaseView
import net.gahfy.feedme.injection.module.ContextModule
import net.gahfy.feedme.injection.module.DaoModule
import net.gahfy.feedme.injection.module.NetworkModule
import net.gahfy.feedme.ui.post.PostPresenter
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class), (DaoModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param postPresenter PostPresenter in which to inject the dependencies
     */
    fun inject(postPresenter: PostPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun daoModule(daoModule: DaoModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}