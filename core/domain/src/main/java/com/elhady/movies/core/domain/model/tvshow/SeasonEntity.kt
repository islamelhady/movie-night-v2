package com.elhady.movies.core.domain.model.tvshow

data class SeasonEntity(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val description: String,
    val year: String,
    val countEpisode: Int,
    val seasonNumber: Int = 0,
)
