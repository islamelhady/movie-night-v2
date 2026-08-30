package com.elhady.movies.core.domain.model.movie

import com.elhady.movies.core.common.MediaType

data class RecommendedMovie(
    val adult: Boolean=false,
    val backdropPath: String="",
    val genreIds: List<Int> = emptyList(),
    val id: Int=0,
    val mediaType: MediaType = MediaType.MOVIE,
    val originalLanguage: String="",
    val originalTitle: String="",
    val overview: String="",
    val popularity: Double=0.0,
    val posterPath: String="",
    val releaseDate: String="",
    val title: String="",
    val video: Boolean=false,
    val voteAverage: Double=0.0,
    val voteCount: Int=0
)
