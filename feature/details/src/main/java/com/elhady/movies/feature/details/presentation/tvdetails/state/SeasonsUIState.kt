package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.feature.details.presentation.seasondetails.SeasonHorizontalUiState

sealed interface SeasonsUIState {

    object Loading : SeasonsUIState

    data class Success(
        val seasons: List<SeasonHorizontalUiState>
    ) : SeasonsUIState

    object Empty : SeasonsUIState

    data class Error(
        val error: ErrorUiState
    ) : SeasonsUIState
}