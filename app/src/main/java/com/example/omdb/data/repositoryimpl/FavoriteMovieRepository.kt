package com.example.omdb.data.repositoryimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.omdb.common.FavoriteMovieList
import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.data.room.dao.FavoriteMovieDAO
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.helpers.ResponseResult
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(private val favoriteMovieDAO: FavoriteMovieDAO) :
    Repository<FavoriteMovieList, FavoriteMovieOperation>, BaseRepository<FavoriteMovieList>() {

    private lateinit var favListLiveData: LiveData<List<FavoriteMovie>>
    private val favList: FavoriteMovieList by lazy {
        FavoriteMovieList()
    }
    private val favListUpdateObserver: Observer<List<FavoriteMovie>> by lazy {
        Observer {
            it?.apply {
                favList.clear()
                favList.addAll(this)
            }
        }
    }

    override suspend fun getData(requestParams: FavoriteMovieOperation): ResponseResult<FavoriteMovieList> {
        return fetchData {
            if (!(this::favListLiveData.isInitialized && favListLiveData.hasActiveObservers())) {
                favListLiveData = favoriteMovieDAO.getFavouritesLiveData()
                favListLiveData.observeForever(favListUpdateObserver)
            }
            when (requestParams) {
                is FavoriteMovieOperation.AddFavoriteMovieOp -> {
                    val list = FavoriteMovieList()
                    list.add(requestParams.data.run {
                        favoriteMovieDAO.insertFavoriteMovie(
                            FavoriteMovie(imdbID, Poster, Title, Year, true)
                        )
                    })
                    list
                }
                is FavoriteMovieOperation.RemoveFavoriteMovieOp -> {
                    val movie = requestParams.data.run {
                        FavoriteMovie(imdbID, Poster, Title, Year, false)
                    }
                    favoriteMovieDAO.removeFromFavorite(movie.imdbID)
                    val list = FavoriteMovieList()
                    list.add(movie)
                    list
                }

                is FavoriteMovieOperation.GetAllFavorites -> {
                    if (favList.isEmpty())
                        favList.addAll(favoriteMovieDAO.getFavourites())
                    favList
                }
            }
        }
    }

    override fun clear() {
        if (this::favListLiveData.isInitialized && favListLiveData.hasActiveObservers()) {
            favListLiveData.removeObserver(favListUpdateObserver)
        }
    }
}