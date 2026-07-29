package com.elhady.movies.core.domain.model.account

import com.elhady.movies.core.domain.model.common.Genre

data class MyRatedTvShow(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<Genre>,
    val rate: Double,
    val year: String = ""
)
