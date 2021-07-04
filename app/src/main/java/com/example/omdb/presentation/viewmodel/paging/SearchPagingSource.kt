package com.example.omdb.presentation.viewmodel.paging

import androidx.paging.PageKeyedDataSource
import com.example.omdb.common.SearchItem
import com.example.omdb.presentation.viewmodel.SearchViewModel

class SearchPagingSource(private val viewModel: SearchViewModel) :
    PageKeyedDataSource<Int, SearchItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchItem>
    ) {
        viewModel.loadInitial(callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        viewModel.loadNext(params, callback)
    }
}