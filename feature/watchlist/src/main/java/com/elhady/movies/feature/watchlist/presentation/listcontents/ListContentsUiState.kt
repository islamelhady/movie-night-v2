package com.elhady.movies.feature.watchlist.presentation.listcontents

import com.elhady.movies.core.ui.base.ErrorUiState

data class ListContentsUiState(
    val title: String = "",
    val movies: List<MovieUiState> = emptyList(),
    val isLoading: Boolean = true,
    val error: ErrorUiState? = null,
){
    val isFailure = error != null
}
