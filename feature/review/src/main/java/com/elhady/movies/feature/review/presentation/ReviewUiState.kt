package com.elhady.movies.feature.review.presentation

data class ReviewUiState(
    val name: String?,
    val avatarPath: String?,
    val content: String?,
    val createdAt: String?,
)

data class ReviewDetailsUiState(
    val page: Int = 1,
    val totalPages: Int = 1,
    val totalReviews: Int = 1
)
