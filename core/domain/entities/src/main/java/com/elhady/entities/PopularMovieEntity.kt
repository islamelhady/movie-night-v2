package com.elhady.entities

data class PopularMovieEntity(
    val movieId: Int,
    val imageUrl: String,
    val title: String,
    val movieRate: Double,
    val genre: List<String>
)