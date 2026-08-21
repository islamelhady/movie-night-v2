package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.PeopleUiState

sealed interface CastUIState {

    object Loading : CastUIState

    data class Success(
        val people: List<PeopleUiState>
    ) : CastUIState

    object Empty : CastUIState

    data class Error(
        val error: ErrorUiState
    ) : CastUIState
}
