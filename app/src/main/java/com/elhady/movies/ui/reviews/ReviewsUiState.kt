package com.elhady.movies.ui.reviews

import com.elhady.movies.ui.models.ReviewUiState
import com.elhady.movies.ui.movieDetails.ErrorUiState

data class ReviewsUiState(
    val review: List<ReviewUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorUiState: List<ErrorUiState> = emptyList()
)
