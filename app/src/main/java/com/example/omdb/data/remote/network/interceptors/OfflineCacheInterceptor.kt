package com.example.omdb.data.remote.network.interceptors

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class OfflineCacheInterceptor @Inject constructor(private val connectivityManager: ConnectivityManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isNetworkAvailable()) {
            val maxStale = 60 * 60 * 24 // Offline cache available for 1 day
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        return chain.proceed(request)
    }

    private fun isNetworkAvailable(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}