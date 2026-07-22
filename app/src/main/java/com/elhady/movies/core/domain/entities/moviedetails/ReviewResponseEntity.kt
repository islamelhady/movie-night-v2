package com.elhady.movies.core.common.domain.entities.moviedetails

import com.elhady.movies.core.common.domain.entities.ReviewEntity


data class ReviewResponseEntity(
    val reviews: List<ReviewEntity> = emptyList(),
    val page: Int = 0,
    val totalPages: Int = 0,
    val totalResults: Int = 0
)
