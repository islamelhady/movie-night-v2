package com.elhady.movies.core.domain.model.movie

import com.elhady.movies.core.domain.model.common.GenreEntity

data class MovieEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val rate: Double,
    val year: String = "",
    val mediaType: String = "movie",
)
