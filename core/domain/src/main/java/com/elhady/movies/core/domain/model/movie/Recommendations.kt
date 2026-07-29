package com.elhady.movies.core.domain.model.movie

data class Recommendations(
    val page: Int = 0,
    val recommendedMovies: List<RecommendedMovie> = emptyList(),
    val totalPages: Int = 0,
    val totalResults: Int=0
)
