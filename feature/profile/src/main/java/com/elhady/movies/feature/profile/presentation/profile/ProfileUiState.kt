package com.elhady.movies.feature.profile.presentation.profile

data class ProfileUiState(
    val username: String = "",
    val avatarUrl: String = "",
    val error: List<String>? = null,
    val isLogIn: Boolean = false,
    val isLoading: Boolean = false
)
