package com.elhady.movies.ui.profile
data class ProfileUiState (
    val avatarPath: String = "",
    val name: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val onErrors: List<String> = emptyList(),
)