package com.elhady.movies.feature.details.presentation.episodedetails

import kotlin.math.roundToInt

data class EpisodeHorizontalUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val description: String,
    val timeEpisode: Int,
    val rate: Double,
    val numberEpisode: Int
) {
    fun formattedRate(): Double = (rate * 10.0).roundToInt() / 10.0

}
