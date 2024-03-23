package com.elhady.movies.core.domain.entities.moviedetails


data class ReviewResponseEntity(
    val reviews: List<ReviewEntity> = emptyList(),
    val page: Int = 0,
    val totalPages: Int = 0,
    val totalResults: Int = 0
)