package com.elhady.movies.core.domain.model.movie

import com.elhady.movies.core.domain.model.common.ReviewEntity

data class ReviewResponseEntity(
    val reviews: List<ReviewEntity> = emptyList(),
    val page: Int = 0,
    val totalPages: Int = 0,
    val totalResults: Int = 0
)
