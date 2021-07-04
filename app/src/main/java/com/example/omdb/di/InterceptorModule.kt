package com.example.omdb.di

import com.example.omdb.data.remote.network.interceptors.AuthInterceptor
import com.example.omdb.data.remote.network.interceptors.OfflineCacheInterceptor
import com.example.omdb.data.remote.network.interceptors.OnlineCacheInterceptor
import dagger.Binds
import dagger.Module
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class InterceptorModule {

    @Binds
    @Singleton
    @Named("authInterceptor")
    abstract fun bindAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor

    @Binds
    @Singleton
    @Named("offlineinterceptor")
    abstract fun bindOfflineCacheInterceptor(offlineCacheInterceptor: OfflineCacheInterceptor): Interceptor

    @Binds
    @Singleton
    @Named("onlineinterceptor")
    abstract fun bindOnlineCacheInterceptor(onlineCacheInterceptor: OnlineCacheInterceptor): Interceptor
}