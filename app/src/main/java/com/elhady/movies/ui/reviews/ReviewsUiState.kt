package com.elhady.movies.ui.reviews

import com.elhady.movies.ui.models.ReviewUiState

data class ReviewsUiState(
    val review: List<ReviewUiState> = emptyList(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)
