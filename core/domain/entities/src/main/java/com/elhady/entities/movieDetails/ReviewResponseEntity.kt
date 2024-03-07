package com.elhady.entities.movieDetails


data class ReviewResponseEntity(
    val reviews: List<ReviewEntity> = emptyList(),
    val page: Int = 0,
    val totalPages: Int = 0,
    val totalResults: Int = 0
)