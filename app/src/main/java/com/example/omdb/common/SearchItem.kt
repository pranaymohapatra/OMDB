package com.example.omdb.common

data class SearchItem(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
) {
    var isFavorite: Boolean = false
    override fun equals(other: Any?): Boolean {
        return other is SearchItem
                && imdbID == other.imdbID
                && isFavorite == other.isFavorite
    }

    override fun hashCode(): Int {
        var result = Poster.hashCode()
        result = 31 * result + Title.hashCode()
        result = 31 * result + Type.hashCode()
        result = 31 * result + Year.hashCode()
        result = 31 * result + imdbID.hashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }
}