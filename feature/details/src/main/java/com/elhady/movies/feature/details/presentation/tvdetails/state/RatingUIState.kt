package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState

data class RatingUIState(
    val rating: Float = 0f,
    val isLoading: Boolean = true,
    val error: ErrorUiState? = null
)