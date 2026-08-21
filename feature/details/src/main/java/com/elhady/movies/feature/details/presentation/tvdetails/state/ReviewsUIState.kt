package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.feature.details.presentation.episodedetails.CommentUiState

sealed interface ReviewsUIState {

    object Loading : ReviewsUIState

    data class Success(
        val reviews: List<CommentUiState>
    ) : ReviewsUIState

    object Empty : ReviewsUIState

    data class Error(
        val error: ErrorUiState
    ) : ReviewsUIState
}