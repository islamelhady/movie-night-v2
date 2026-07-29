package com.elhady.movies.core.domain.model.movie

import com.elhady.movies.core.domain.model.common.Review

data class ReviewResponse(
    val reviews: List<Review> = emptyList(),
    val page: Int = 0,
    val totalPages: Int = 0,
    val totalResults: Int = 0
)
