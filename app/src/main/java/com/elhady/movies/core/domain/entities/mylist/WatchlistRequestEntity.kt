package com.elhady.movies.core.domain.entities.mylist



data class WatchlistRequestEntity(
    val watchlist: Boolean?,
    val mediaId: Int?,
    val mediaType: String,
)