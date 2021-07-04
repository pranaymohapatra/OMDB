package com.example.omdb.helpers

data class GlobalState(val state: State, val message: String? = null)
enum class State {
    ERROR,
    SUCCESS,
    LOADING
}