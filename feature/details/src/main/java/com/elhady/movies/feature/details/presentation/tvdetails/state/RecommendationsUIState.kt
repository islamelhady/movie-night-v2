package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.MediaVerticalUiState

sealed interface RecommendationsUIState {

    object Loading : RecommendationsUIState

    data class Success(
        val items: List<MediaVerticalUiState>
    ) : RecommendationsUIState

    object Empty : RecommendationsUIState

    data class Error(
        val error: ErrorUiState
    ) : RecommendationsUIState
}