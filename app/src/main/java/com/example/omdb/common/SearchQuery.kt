package com.example.omdb.common

data class SearchQuery(
    val query: String,
    var page: Int = 1
)