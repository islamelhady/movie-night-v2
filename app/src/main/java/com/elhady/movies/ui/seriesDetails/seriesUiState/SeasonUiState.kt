package com.elhady.movies.ui.seriesDetails.seriesUiState

data class SeasonUiState(
    val seasonId: Int = 0,
    val seasonName: String = "",
    val seasonPoster: String = "",
    val seasonOverview: String = "",
    val seasonYear: String = "",
    val seasonNumber: Int = 0,
    val seasonEpisodeCount: Int = 0
)
