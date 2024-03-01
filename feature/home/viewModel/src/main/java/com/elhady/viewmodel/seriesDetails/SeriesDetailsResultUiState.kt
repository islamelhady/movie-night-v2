package com.elhady.viewmodel.seriesDetails

data class SeriesDetailsResultUiState(
    val seriesId: Int = 0,
    val seriesName: String = "",
    val seriesImage: String = "",
    val seriesReleaseDate: String = "",
    val seriesSeasonsNumber: Int = 0,
    val seriesGenres: String = "",
    val seriesReview: Int = 0,
    val seriesVoteAverage: String = "",
    val seriesOverview: String = "",
)
