package com.elhady.movies.core.domain.model.myrated

import com.elhady.movies.core.domain.model.GenreEntity


data class MyRatedTvShowEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val rate: Double,
    val year: String = ""
)
