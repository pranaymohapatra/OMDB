package com.example.omdb.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.omdb.data.room.entities.FavoriteMovie

@Dao
interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(vararg favoriteMovie: FavoriteMovie): FavoriteMovie

    @Query("DELETE FROM Favorite_Movies where imdbID = :movieId")
    fun removeFromFavorite(movieId: String): Int

    @Query("SELECT * FROM Favorite_Movies")
    fun getFavourites(): List<FavoriteMovie>

    @Query("SELECT * FROM Favorite_Movies")
    fun getFavouritesLiveData(): LiveData<List<FavoriteMovie>>
}