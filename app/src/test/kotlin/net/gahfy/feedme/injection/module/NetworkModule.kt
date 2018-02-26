package net.gahfy.feedme.injection.module

import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import net.gahfy.feedme.model.Post
import net.gahfy.feedme.network.PostApi
import net.gahfy.feedme.utils.ApiUtils
import net.gahfy.feedme.utils.BASE_URL
import net.gahfy.feedme.utils.POST_MOCK_PATH
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {
    @Provides
    @Singleton
    internal fun providePostApi(retrofit: Retrofit): PostApi {
        return object : PostApi {
            override fun getPosts(): Observable<List<Post>> {
                return Observable.fromCallable { ApiUtils.getUrl<List<Post>>(POST_MOCK_PATH) }
            }
        }
    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).build()
    }
}