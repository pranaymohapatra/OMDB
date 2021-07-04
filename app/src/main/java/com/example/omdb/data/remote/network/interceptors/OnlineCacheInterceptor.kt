package com.example.omdb.data.remote.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OnlineCacheInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val maxAge = 60 // read from cache for 1 mins even if there is internet connection
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
}
