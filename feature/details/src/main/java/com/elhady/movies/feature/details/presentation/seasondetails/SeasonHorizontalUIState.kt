package com.elhady.movies.feature.details.presentation.seasondetails

data class SeasonHorizontalUIState(
    val id: Int = 0,
    val imageUrl: String = "",
    val title: String = "",
    val description: String = "",
    val year: String = "",
    val countEpisode: Int = 0,
    val seasonNumber: Int = 1,
)