package com.example.omdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.omdb.common.SearchItem
import com.example.omdb.domain.usecase.SearchMoviesUseCase
import com.example.omdb.helpers.GlobalState
import com.example.omdb.helpers.State
import com.example.omdb.presentation.viewmodel.paging.SearchSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : BaseViewModel() {
    private lateinit var pagedSearchList: LiveData<PagedList<SearchItem>>
    private val dataSourceFactory = SearchSourceFactory(this)
    private val pagedListBuilder: LivePagedListBuilder<Int, SearchItem>
    private var query = ""
    private lateinit var searchJob: Job
    val noItemsData: LiveData<String>
        get() = _noItemsData
    private val _noItemsData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val searchLoader: LiveData<Boolean>
        get() = _searchLoader
    private val _searchLoader: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
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

    fun searchMovie(searchQuery: String) {
        cancelJobIfActive()
        _searchLoader.value = false
        if (query == searchQuery) {
            query = ""
            return
        } else {
            searchJob = executeJob {
                withContext(Dispatchers.IO) {
                    delay(500) //Debounce Timeout
                    _searchLoader.postValue(true)
                }
                if (!this@SearchViewModel::pagedSearchList.isInitialized)
                    pagedSearchList = pagedListBuilder.build()
                else
                    retryLoad()
            }

        }
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
        pagedSearchList.value?.clear()
        dataSourceFactory.refresh()
    }


    private fun cancelJobIfActive() {
        if (this::searchJob.isInitialized && searchJob.isActive) {
            _globalStateData.value = GlobalState(State.SUCCESS, "")
            searchJob.cancel()
        }
    }

}