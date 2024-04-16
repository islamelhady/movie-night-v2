package com.elhady.movies.core.domain.entities.myrated

import com.elhady.movies.core.domain.entities.GenreEntity


data class MyRatedMovieEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val myRate: Double,
    val year: String = ""
)
