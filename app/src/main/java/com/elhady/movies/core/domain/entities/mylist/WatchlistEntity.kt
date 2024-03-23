package com.elhady.movies.core.domain.entities.mylist

data class WatchlistEntity(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val rate: Double,
    val year: String,
    val mediaType: String,
)