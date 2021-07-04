package com.example.omdb.helpers

sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val message: String?, val throwable: Throwable? = null) :
        ResponseResult<Nothing>()
}

val ResponseResult<*>.succeeded
    get() = this is ResponseResult.Success && data != null

fun <T> ResponseResult<T>.getResultData(): T {
    this as ResponseResult.Success<T>
    return data
}