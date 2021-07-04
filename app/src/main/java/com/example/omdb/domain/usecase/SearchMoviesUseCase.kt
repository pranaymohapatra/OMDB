package com.example.omdb.domain.usecase

import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.common.SearchItem
import com.example.omdb.common.SearchQuery
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.domain.dto.SearchDomainDTO
import com.example.omdb.helpers.ResponseResult
import com.example.omdb.helpers.getResultData
import com.example.omdb.helpers.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val searchRepository: Repository<SearchDomainDTO, SearchQuery>,
    private val favoriteMovieRepository: Repository<List<FavoriteMovie>, FavoriteMovieOperation>
) {
    private val favMap: MutableMap<String, Boolean> by lazy {
        mutableMapOf()
    }

    suspend fun getSearchResults(
        searchQuery: String,
        page: Int = 1
    ): ResponseResult<SearchDomainDTO> {
        return withContext(Dispatchers.IO) {
            val favResponse =
                favoriteMovieRepository.getData(FavoriteMovieOperation.GetAllFavorites)
            if (favResponse.succeeded) {
                favResponse.getResultData().forEach {
                    favMap.clear()
                    favMap[it.imdbID] = true
                }
            }
            val deferredSearchList = async {
                searchRepository.getData(requestParams = SearchQuery(searchQuery, page))
            }
            if (favMap.isNullOrEmpty()) {
                val deferredFavList = async {
                    favoriteMovieRepository.getData(FavoriteMovieOperation.GetAllFavorites)
                }
                val favListResult = deferredFavList.await()
                if (favListResult.succeeded) {
                    favMap.apply {
                        clear()
                        favListResult.getResultData().forEach { mov ->
                            favMap[mov.imdbID] = true
                        }
                    }
                }
            }
            val searchResult = deferredSearchList.await()
            if (searchResult.succeeded) {
                updateSearchFav(searchResult.getResultData().searchItems)
            }
            searchResult
        }
    }

    private fun updateSearchFav(searchList: List<SearchItem>) {
        searchList.forEach { searchItem ->
            if (favMap.containsKey(searchItem.imdbID))
                searchItem.isFavorite = true
        }
    }

    fun clear() {
        favoriteMovieRepository.clear()
        searchRepository.clear()
    }
}