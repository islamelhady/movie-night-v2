package com.elhady.movies.core.domain.entities.myrated

import com.elhady.movies.core.domain.entities.GenreEntity


data class MyRatedTvShowEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val rate: Double,
    val year: String = ""
)
