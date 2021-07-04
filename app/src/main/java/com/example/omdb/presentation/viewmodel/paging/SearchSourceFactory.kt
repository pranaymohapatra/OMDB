package com.example.omdb.presentation.viewmodel.paging

import androidx.paging.DataSource
import com.example.omdb.common.SearchItem
import com.example.omdb.presentation.viewmodel.SearchViewModel

class SearchSourceFactory(private val searchViewModel: SearchViewModel) :
    DataSource.Factory<Int, SearchItem>() {
    private var dataSource: SearchPagingSource? = null
    override fun create(): DataSource<Int, SearchItem> {
        dataSource = SearchPagingSource(searchViewModel)
        return dataSource!!
    }

    fun refresh() {
        dataSource?.invalidate()
    }
}