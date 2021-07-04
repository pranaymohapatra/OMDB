package com.example.omdb.di

import androidx.lifecycle.LiveData
import com.example.omdb.common.FavoriteMovieOperation
import com.example.omdb.common.SearchQuery
import com.example.omdb.data.repositoryimpl.FavoriteMovieLiveDataRepository
import com.example.omdb.data.repositoryimpl.FavoriteMovieRepository
import com.example.omdb.data.repositoryimpl.MovieDetailsRepository
import com.example.omdb.data.repositoryimpl.SearchRepository
import com.example.omdb.data.room.entities.FavoriteMovie
import com.example.omdb.domain.Repository
import com.example.omdb.domain.dto.MovieDetailDTO
import com.example.omdb.domain.dto.SearchDomainDTO
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepo(searchRepository: SearchRepository)
            : Repository<SearchDomainDTO, SearchQuery>

    @Binds
    @Singleton
    abstract fun bindSharedPrefRepo(movieDetailsRepository: MovieDetailsRepository)
            : Repository<MovieDetailDTO, String>

    @Binds
    @Singleton
    abstract fun bindFavMovieRepoUpdate(favoriteMovieAddRemoveRepository: FavoriteMovieRepository)
            : Repository<List<FavoriteMovie>, FavoriteMovieOperation>

    @Binds
    @Singleton
    abstract fun bindFavMovieLiveDataRepoUpdate(favoriteMovieLiveDataRepository: FavoriteMovieLiveDataRepository)
            : Repository<LiveData<List<FavoriteMovie>>, FavoriteMovieOperation.GetAllFavorites>

}