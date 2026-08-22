package com.elhady.movies.feature.profile.presentation.profile

import com.elhady.movies.core.ui.base.ErrorUiState

data class ProfileUiState(
    val username: String = "",
    val avatarUrl: String = "",
    val errors: ErrorUiState? = null,
    val isLogIn: Boolean = false,
    val isLoading: Boolean = false
)
