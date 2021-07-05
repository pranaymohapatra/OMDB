package com.example.omdb.di

import android.content.Context
import androidx.room.Room
import com.example.omdb.data.room.FavoriteMovieDB
import com.example.omdb.data.room.dao.FavoriteMovieDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDb(context: Context): FavoriteMovieDB {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteMovieDB::class.java,
            "FavoriteMovieDB"
        ).build()
    }

    @Singleton
    @Provides
    fun providesFavMovieDao(favoriteMovieDB: FavoriteMovieDB): FavoriteMovieDAO {
        return favoriteMovieDB.favoriteMovieDao
    }

}