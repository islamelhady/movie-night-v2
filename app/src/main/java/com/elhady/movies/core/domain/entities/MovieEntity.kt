package com.elhady.movies.core.domain.entities

data class MovieEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val rate: Double,
    val year: String = "",
    val mediaType: String = "movie",
)