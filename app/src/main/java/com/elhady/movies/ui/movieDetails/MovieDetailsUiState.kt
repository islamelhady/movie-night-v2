package com.elhady.movies.ui.movieDetails

data class MovieDetailsUiState(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val overview: String = "",
    val specialNumber: Int = 0,
    val minutes: Int = 0,
    val hours: Int = 0,
    val voteAverage: String = "",
    val genres: String = "",
    val review: Int = 0,
    val releaseDate: String = ""
)
