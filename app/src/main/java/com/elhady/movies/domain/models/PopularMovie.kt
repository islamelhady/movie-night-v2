package com.elhady.movies.domain.models

data class PopularMovie(
    val movieId: Int,
    val imageUrl: String,
    val title: String,
    val movieRate: Double,
    val genre: List<String>
)