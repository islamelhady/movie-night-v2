package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.core.ui.base.ErrorUiState

sealed interface InfoUIState {
    object Loading : InfoUIState

    data class Success(
        val info: Info
    ) :InfoUIState

    data class Error(
        val error: ErrorUiState
    ) : InfoUIState

    data class Info(
        val backdropImageUrl: String = "",
        val name: String = "",
        val rating: Float = 0f,
        val description: String = "",
        val genres: List<String> = emptyList(),
        val isLogin: Boolean = false,
    )
}