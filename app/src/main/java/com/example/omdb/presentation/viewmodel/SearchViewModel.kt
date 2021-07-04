package com.example.omdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.omdb.common.SearchItem
import com.example.omdb.domain.usecase.SearchMoviesUseCase
import com.example.omdb.presentation.viewmodel.paging.SearchSourceFactory
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : BaseViewModel() {
    private lateinit var pagedSearchList: LiveData<PagedList<SearchItem>>
    private val dataSourceFactory = SearchSourceFactory(this)
    private val pagedListBuilder: LivePagedListBuilder<Int, SearchItem>
    private var query = ""

    val noItemsData: LiveData<String>
        get() = _noItemsData
    private val _noItemsData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setMaxSize(40).build()
        pagedListBuilder = LivePagedListBuilder(
            dataSourceFactory,
            pagedListConfig
        ).setBoundaryCallback(object : PagedList.BoundaryCallback<SearchItem>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                _noItemsData.value = "Cannot Find Movies"
            }
        })
    }

    fun searchMovie() {
        pagedSearchList = pagedListBuilder.build()
    }

    fun loadInitial(callback: PageKeyedDataSource.LoadInitialCallback<Int, SearchItem>) {
        startBackgroundJob({
            searchMoviesUseCase.getSearchResults(query)
        }, {
            callback.onResult(it.searchItems, 0, it.totalResults, null, 2)
        })
    }

    fun loadNext(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, SearchItem>
    ) {
        startBackgroundJob({
            searchMoviesUseCase.getSearchResults(query, params.key)
        }, {
            callback.onResult(it.searchItems, params.key + 1)
        })
    }

    fun retryLoad() {
        dataSourceFactory.refresh()
    }

}