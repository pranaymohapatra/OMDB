package com.example.omdb.data.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite_Movies", indices = [Index(value = ["imdbID"])])
data class FavoriteMovie(
    @PrimaryKey val imdbID: String,
    val Poster: String,
    val Title: String,
    val Year: String,
    val isFavorite: Boolean,
)