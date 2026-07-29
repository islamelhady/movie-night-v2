package com.elhady.movies.core.domain.model.people

data class People(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val popularity: Double = 0.0
)
