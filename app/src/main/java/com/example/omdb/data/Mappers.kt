package com.example.omdb.data

import com.example.omdb.data.dto.MovieDetailResponse
import com.example.omdb.data.dto.MovieSearchResponse
import com.example.omdb.domain.dto.MovieDetailDTO
import com.example.omdb.domain.dto.SearchDomainDTO

fun MovieSearchResponse.mapToDomain(): SearchDomainDTO {
    return SearchDomainDTO(
        totalResults = totalResults.toInt(),
        searchItems = Search
    )
}

fun MovieDetailResponse.mapToDomain(): MovieDetailDTO {
    return MovieDetailDTO(
        Actors, Awards, Country, Director, Genre, Language, Metascore,
        Plot, Poster, Production, Rated, Released, Response, Runtime, Title,
        Writer, Year, imdbID, imdbRating, imdbVotes
    )
}