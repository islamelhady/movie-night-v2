package com.elhady.movies.presentation.viewmodel.profile

data class ProfileUIState(
    val username: String = "",
    val avatarUrl: String = "",
    val error: List<String>? = null,
    val isLogIn: Boolean = false,
    val isLoading: Boolean = false
)
