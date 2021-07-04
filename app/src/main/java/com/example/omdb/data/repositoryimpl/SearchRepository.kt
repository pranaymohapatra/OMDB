package com.example.omdb.data.repositoryimpl

import com.example.omdb.common.SearchQuery
import com.example.omdb.data.mapToDomain
import com.example.omdb.data.remote.OMDBApiService
import com.example.omdb.domain.Repository
import com.example.omdb.domain.dto.SearchDomainDTO
import com.example.omdb.helpers.ResponseResult
import javax.inject.Inject

class SearchRepository @Inject constructor(private val movieApi: OMDBApiService) :
    Repository<SearchDomainDTO, SearchQuery>, BaseRepository<SearchDomainDTO>() {

    override suspend fun getData(requestParams: SearchQuery): ResponseResult<SearchDomainDTO> {
        return fetchData {
            movieApi.getSearchResult(
                requestParams.query,
                requestParams.page
            ).mapToDomain()
        }
    }

    override fun clear() {

    }
}
