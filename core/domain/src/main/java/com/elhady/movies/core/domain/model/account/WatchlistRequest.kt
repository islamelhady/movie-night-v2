package com.elhady.movies.core.domain.model.account

data class WatchlistRequest(
    val mediaId: Int,
    val mediaType: String,
    val watchlist: Boolean
)
