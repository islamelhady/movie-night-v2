package com.elhady.movies.domain.models

data class TVShowDetails(
    val tvShowId: Int,
    val tvShowImage: String,
    val tvShowName: String,
    val tvShowReleaseDate: String,
    val tvShowReview: Int,
    val tvShowGenres: String,
    val tvShowVoteAverage: String,
    val tvShowSeasonsNumber: Int,
    val tvShowOverview: String,
)
