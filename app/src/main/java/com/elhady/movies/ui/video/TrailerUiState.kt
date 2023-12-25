package com.elhady.movies.ui.video

data class TrailerUiState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)