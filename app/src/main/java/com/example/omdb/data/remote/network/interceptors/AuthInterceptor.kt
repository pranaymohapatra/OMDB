package com.example.omdb.data.remote.network.interceptors

import com.example.omdb.data.remote.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("key", NetworkConstants.apiKey)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}