package com.elhady.viewmodel.video

data class TrailerUiState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)