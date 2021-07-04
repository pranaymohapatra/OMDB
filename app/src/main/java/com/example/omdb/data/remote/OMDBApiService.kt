package com.example.omdb.data.remote

import com.example.omdb.data.dto.MovieDetailResponse
import com.example.omdb.data.dto.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBApiService {

    @GET()
    suspend fun getSearchResult(
        @Query("s") query: String,
        @Query("page") page: Int
    ): MovieSearchResponse


    @GET
    suspend fun getMovieDetails(
        @Query("i") movieId: String
    ): MovieDetailResponse
}