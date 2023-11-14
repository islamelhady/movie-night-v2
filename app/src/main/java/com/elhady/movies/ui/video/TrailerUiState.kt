package com.elhady.movies.ui.video

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class TrailerUiState(
    val videoKey: String = "",
    val isLoading: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)