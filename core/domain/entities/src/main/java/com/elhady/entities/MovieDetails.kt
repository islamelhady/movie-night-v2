package com.elhady.entities


data class MovieDetails(
    val id: Int,
    val name: String,
    val image: String,
    val releaseDate: String,
    val overview: String,
    val voteAverage: String,
    val duration: Int,
    val review: Int,
    val genres: String
)
