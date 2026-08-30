package com.elhady.movies.core.ui.state


data class UserListUiState(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false,
    val isLoading: Boolean = true,
    val error: Boolean = false
)
