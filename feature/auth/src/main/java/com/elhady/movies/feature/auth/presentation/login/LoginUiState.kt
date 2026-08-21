package com.elhady.movies.feature.auth.presentation.login

data class LoginUiState(
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
)