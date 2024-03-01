package com.elhady.viewmodel.reviews

import com.elhady.viewmodel.models.ReviewUiState

data class ReviewsUiState(
    val review: List<ReviewUiState> = emptyList(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)
