package com.elhady.movies.core.domain.model.account

import com.elhady.movies.core.domain.model.common.GenreEntity

data class MyRatedTvShowEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val rate: Double,
    val year: String = ""
)
