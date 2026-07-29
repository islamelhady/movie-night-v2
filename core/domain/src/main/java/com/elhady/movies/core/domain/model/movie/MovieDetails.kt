package com.elhady.movies.core.domain.model.movie

data class MovieDetails(
    val backdropPath: String = "",
    val credits: Credits,
    val genres: List<String> = emptyList(),
    val id: Int = 0,
    val overview: String= "",
    val recommendations: Recommendations,
    val title: String= "",
    val video: Boolean = false,
    val videos: Videos,
    val voteAverage: Double = 0.0,
    val reviewEntity: ReviewResponse,
    val year: String
)
