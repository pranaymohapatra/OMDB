package com.example.omdb.di

import android.content.Context
import com.example.omdb.data.remote.NetworkConstants
import com.example.omdb.data.remote.OMDBApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [InterceptorModule::class])
class HttpModule {

    @Provides
    @Singleton
    fun getRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.OMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun getOkHttpClient(
        @Named("authInterceptor") authInterceptor: Interceptor,
        @Named("offlineinterceptor") offlineCacheInterceptor: Interceptor,
        @Named("onlineinterceptor") onlineCacheInterceptor: Interceptor,
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .addNetworkInterceptor(onlineCacheInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun getApiInterface(retroFit: Retrofit): OMDBApiService {
        return retroFit.create(OMDBApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCacheInstance(context: Context): Cache {
        val cacheSize = 2 * 1024 * 1024 // 2 MB Cache
        val cache = Cache(context.getCacheDir(), cacheSize.toLong())
        return cache
    }
}