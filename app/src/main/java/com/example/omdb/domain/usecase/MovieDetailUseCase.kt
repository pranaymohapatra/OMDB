package com.example.omdb.domain.usecase

import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.domain.dto.MovieDetailDTO
import com.example.omdb.helpers.ResponseResult
import com.example.omdb.helpers.getResultData
import com.example.omdb.helpers.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val movieDetailRepo: Repository<MovieDetailDTO, String>,
    private val favoriteMovieRepository: Repository<List<FavoriteMovie>, FavoriteMovieOperation>
) {

    private val favMap: MutableMap<String, Boolean> by lazy {
        mutableMapOf()
    }


    suspend fun getMovieDetails(imdbId: String): ResponseResult<MovieDetailDTO> {
        return withContext(Dispatchers.IO) {
            val favResponse =
                favoriteMovieRepository.getData(FavoriteMovieOperation.GetAllFavorites)
            if (favResponse.succeeded) {
                favResponse.getResultData().forEach {
                    favMap.clear()
                    favMap[it.imdbID] = true
                }
            }

            val movieDetailsDeffered = async { movieDetailRepo.getData(imdbId) }
            if (favMap.isNullOrEmpty()) {
                val favListResult =
                    favoriteMovieRepository.getData(FavoriteMovieOperation.GetAllFavorites)
                if (favListResult.succeeded) {
                    favMap.apply {
                        clear()
                        favListResult.getResultData().forEach { mov ->
                            favMap[mov.imdbID] = true
                        }
                    }
                }
            }
            val result = movieDetailsDeffered.await()
            if (result.succeeded)
                result.getResultData().apply {
                    if (favMap.containsKey(imdbID))
                        isFavorite = true
                }
            result
        }
    }

    fun clear() {
        favoriteMovieRepository.clear()
        movieDetailRepo.clear()
    }
}