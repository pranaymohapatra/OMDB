package com.example.omdb.data.repositoryimpl

import com.example.omdb.data.mapToDomain
import com.example.omdb.data.remote.OMDBApiService
import com.example.omdb.domain.Repository
import com.example.omdb.domain.dto.MovieDetailDTO
import com.example.omdb.helpers.ResponseResult
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val movieApi: OMDBApiService) :
    Repository<MovieDetailDTO, String>, BaseRepository<MovieDetailDTO>() {

    override suspend fun getData(requestParams: String): ResponseResult<MovieDetailDTO> {
        return fetchData {
            movieApi.getMovieDetails(requestParams).mapToDomain()
        }
    }

    override fun clear() {

    }

}