package com.elhady.movies.core.common

enum class MediaType(val value: String) {
    MOVIE("movie"),
    TV_SHOW("tv")
}

fun String?.toMediaType(): MediaType {
    return when (this) {
        "movie" -> MediaType.MOVIE
        "tv" -> MediaType.TV_SHOW
        else -> MediaType.MOVIE 
    }
}
