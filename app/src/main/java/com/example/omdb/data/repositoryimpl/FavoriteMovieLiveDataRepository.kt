package com.example.omdb.data.repositoryimpl

import androidx.lifecycle.LiveData
import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.data.room.dao.FavoriteMovieDAO
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.helpers.ResponseResult
import javax.inject.Inject

class FavoriteMovieLiveDataRepository @Inject constructor(private val favoriteMovieDAO: FavoriteMovieDAO) :
    Repository<LiveData<List<FavoriteMovie>>, FavoriteMovieOperation.GetAllFavorites>,
    BaseRepository<LiveData<List<FavoriteMovie>>>() {

    override suspend fun getData(requestParams: FavoriteMovieOperation.GetAllFavorites)
            : ResponseResult<LiveData<List<FavoriteMovie>>> {
        return fetchData {
            favoriteMovieDAO.getFavouritesLiveData()
        }
    }
}