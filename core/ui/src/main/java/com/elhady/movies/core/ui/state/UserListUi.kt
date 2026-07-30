package com.elhady.movies.core.ui.state


data class UserListUi(
    val id: Int,
    val name: String,
    val isLoading: Boolean = true,
    val error: Boolean = false
)
