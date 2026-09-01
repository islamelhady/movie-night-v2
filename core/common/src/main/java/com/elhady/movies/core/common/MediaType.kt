package com.elhady.movies.core.common

enum class MediaType(val value: String) {
    MOVIE("movie"),
    TV_SHOW("tv")
}

fun String?.toMediaType(): MediaType? {
    return when (this) {
        MediaType.MOVIE.value -> MediaType.MOVIE
        MediaType.TV_SHOW.value -> MediaType.TV_SHOW
        else -> null
    }
}
