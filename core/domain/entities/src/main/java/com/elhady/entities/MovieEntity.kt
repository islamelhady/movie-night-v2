package com.elhady.entities

data class MovieEntity(
    val movieId: Int,
    val movieName: String,
    val movieType:String = "movie",
    val movieGenreEntities: List<GenreEntity>,
    val movieRate: Float,
    val movieImage: String,
    val movieYear: String,
)