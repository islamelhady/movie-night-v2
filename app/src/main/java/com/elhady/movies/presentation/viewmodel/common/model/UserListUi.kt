package com.elhady.movies.presentation.viewmodel.common.model


data class UserListUi(
    val id: Int,
    val name: String,
    val isLoading: Boolean = true,
    val error: Boolean = false
)
