package com.example.omdb.domain.usecase

import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.helpers.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoritesUseCase
@Inject constructor(private val favoriteMovieRepository: Repository<List<FavoriteMovie>, FavoriteMovieOperation>) {
    suspend fun getFavoriteMovies(): ResponseResult<List<FavoriteMovie>> {
        return withContext(Dispatchers.IO) {
            favoriteMovieRepository.getData(FavoriteMovieOperation.GetAllFavorites)
        }
    }

    fun clear() {
        favoriteMovieRepository.clear()
    }
}