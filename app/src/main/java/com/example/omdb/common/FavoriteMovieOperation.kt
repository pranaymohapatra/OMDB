package com.example.omdb.common

sealed class FavoriteMovieOperation(
    val operationType: OperationType,
    val data: SearchItem
) {
    class AddFavoriteMovieOp(data: SearchItem) :
        FavoriteMovieOperation(OperationType.INSERT, data)

    class RemoveFavoriteMovieOp(data: SearchItem) :
        FavoriteMovieOperation(OperationType.DELETE, data)

    object GetAllFavorites :
        FavoriteMovieOperation(
            OperationType.SELECTALL,
            SearchItem("", "", "", "", "")
        )
}

enum class OperationType {
    INSERT, DELETE, SELECTALL
}