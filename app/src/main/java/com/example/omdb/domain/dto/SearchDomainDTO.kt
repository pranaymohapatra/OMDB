package com.example.omdb.domain.dto

import com.example.omdb.common.SearchItem

data class SearchDomainDTO(val totalResults: Int, val searchItems: List<SearchItem>)