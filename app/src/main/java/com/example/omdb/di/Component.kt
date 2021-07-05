package com.example.omdb.di

import android.content.Context
import com.example.omdb.presentation.view.BaseFragment
import com.starwars.starwarstournament.presentation.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [HttpModule::class,
        ViewModelModule::class,
        MainAppModule::class,
        RepositoryModule::class,
        RoomModule::class]
)
@Singleton
interface Component {
    fun inject(mainActivity: MainActivity)
    fun inject(baseFragment: BaseFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activityContext(context: Context): Builder

        fun build(): com.example.omdb.di.Component
    }
}