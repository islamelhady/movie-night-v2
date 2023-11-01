package com.elhady.movies.ui.tvShowDetails

data class TVShowDetailsResultUiState(
    val tvShowId: Int = 0,
    val tvShowName: String = "",
    val tvShowImage: String = "",
    val tvShowReleaseDate: String = "",
    val tvShowSeasonsNumber: Int = 0,
    val tvShowGenres: String = "",
    val tvShowReview: Int = 0,
    val tvShowVoteAverage: String = "",
    val tvShowOverview: String = "",
)
