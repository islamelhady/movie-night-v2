package com.elhady.entities
data class SaveListDetailsEntity(
    val id: Int,
    val mediaType: String,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterPath: String,
)