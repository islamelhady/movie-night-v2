package com.elhady.movies.domain.models

data class Season(
    val seasonId: Int,
    val seasonName: String,
    val seasonPoster: String,
    val seasonOverview: String,
    val seasonYear: String,
    val seasonNumber: Int,
    val seasonEpisodeCount: Int
)
