package com.elhady.entities


data class SeriesDetailsEntity(
    val seriesId: Int,
    val seriesImage: String,
    val seriesName: String,
    val seriesReleaseDate: String,
    val seriesReview: Int,
    val seriesGenres: String,
    val seriesVoteAverage: String,
    val seriesSeasonsNumber: Int,
    val seriesOverview: String,
    val seriesSeasons: List<SeasonEntity>
)
