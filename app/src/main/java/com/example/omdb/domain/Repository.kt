package com.example.omdb.domain

import com.example.omdb.helpers.ResponseResult


interface Repository<T, Params> {
    suspend fun getData(requestParams: Params): ResponseResult<T>

    fun clear()
}