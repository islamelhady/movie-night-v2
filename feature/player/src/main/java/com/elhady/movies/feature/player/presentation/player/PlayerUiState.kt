package com.elhady.movies.feature.player.presentation.player

import com.elhady.movies.core.ui.base.ErrorUiState

data class PlayerUiState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val errors: ErrorUiState? = null,
)
