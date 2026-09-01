package com.elhady.movies.feature.auth.presentation.login

import com.elhady.movies.core.ui.base.UiText

data class LoginUiState(
    val username: String = "",
    val usernameError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isLoading: Boolean = false,
)