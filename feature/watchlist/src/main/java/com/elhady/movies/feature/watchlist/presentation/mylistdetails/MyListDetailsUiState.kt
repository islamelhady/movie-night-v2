package com.elhady.movies.feature.watchlist.presentation.mylistdetails

import com.elhady.movies.core.ui.base.ErrorUiState

data class MyListDetailsUiState(
    val title: String = "",
    val movies: List<MovieUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
){
    val isFailure = error != null
}
