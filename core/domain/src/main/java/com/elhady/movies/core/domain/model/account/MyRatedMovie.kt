package com.elhady.movies.core.domain.model.account

import com.elhady.movies.core.domain.model.common.Genre

data class MyRatedMovie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<Genre>,
    val myRate: Double,
    val year: String = ""
)
