package com.example.omdb.data.dto

import com.example.omdb.common.SearchItem

data class MovieSearchResponse(
    val Response: String,
    val Search: List<SearchItem>,
    val totalResults: String
)