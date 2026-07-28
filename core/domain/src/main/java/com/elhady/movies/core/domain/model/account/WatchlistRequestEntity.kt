package com.elhady.movies.core.domain.model.account

data class WatchlistRequestEntity(
    val mediaId: Int,
    val mediaType: String,
    val watchlist: Boolean
)
