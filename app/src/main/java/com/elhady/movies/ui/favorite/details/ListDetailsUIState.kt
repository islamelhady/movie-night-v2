package com.elhady.movies.ui.favorite.details

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class ListDetailsUIState(
    val savedMedia: List<SavedMediaUIState> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)