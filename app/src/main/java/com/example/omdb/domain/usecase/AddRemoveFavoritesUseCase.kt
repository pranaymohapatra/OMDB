package com.example.omdb.domain.usecase

import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.common.SearchItem
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.helpers.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AddRemoveFavoritesUseCase @Inject constructor(
    private val favoriteMovieRepository: Repository<List<FavoriteMovie>, FavoriteMovieOperation>
) {

    suspend fun getData(item: SearchItem): ResponseResult<List<FavoriteMovie>> {
        return withContext(Dispatchers.IO) {
            val operation = if (item.isFavorite) {
                FavoriteMovieOperation.AddFavoriteMovieOp(item)
            } else
                FavoriteMovieOperation.RemoveFavoriteMovieOp(item)
            favoriteMovieRepository.getData(operation)
        }
    }
}