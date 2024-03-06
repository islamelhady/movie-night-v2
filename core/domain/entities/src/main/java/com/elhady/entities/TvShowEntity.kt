package com.elhady.entities

data class TvShowEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rate: Double,
    val tvShowGenreEntities: List<GenreEntity>
)
