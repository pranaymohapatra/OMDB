package com.example.omdb.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainAppModule {

    @Provides
    @Singleton
    fun provideConnectivityClient(applicationContext: Context): ConnectivityManager {
        return applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}