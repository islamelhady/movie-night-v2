package com.elhady.entities

data class PopularMovieEntity(
    val movieId: Int,
    val movieImage: String,
    val movieName: String,
    val movieRate: Double,
    val movieGenre: List<String>
)