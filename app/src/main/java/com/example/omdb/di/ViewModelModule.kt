package com.example.omdb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun getVMFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    @Singleton
    abstract fun getSearchViewModel(searchViewModel: SearchViewModel): ViewModel

}