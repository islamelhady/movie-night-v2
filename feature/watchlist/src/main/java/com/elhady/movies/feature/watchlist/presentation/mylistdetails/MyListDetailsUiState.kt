package com.elhady.movies.feature.watchlist.presentation.mylistdetails

import com.elhady.movies.core.ui.base.ErrorUiState

data class MyListDetailsUiState(
    val movies: List<MovieUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
)
