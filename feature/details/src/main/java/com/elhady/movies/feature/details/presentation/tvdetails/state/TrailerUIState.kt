package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState

sealed interface TrailerUIState {

    object Loading : TrailerUIState

    data class Available(
        val youtubeKey: Trailer
    ) : TrailerUIState

    object NotAvailable : TrailerUIState

    data class Error(
        val error: ErrorUiState
    ) : TrailerUIState

    data class Trailer(
        val youtubeKey: String
    )

}