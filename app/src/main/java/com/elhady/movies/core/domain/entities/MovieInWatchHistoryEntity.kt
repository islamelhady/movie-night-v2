package com.elhady.movies.core.domain.entities

import java.util.Date

data class MovieInWatchHistoryEntity(
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val description:String,
    val dateWatched: Date,
    val year:Int
)