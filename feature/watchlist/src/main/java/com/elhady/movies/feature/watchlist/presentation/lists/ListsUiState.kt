package com.elhady.movies.feature.watchlist.presentation.lists

import com.elhady.movies.core.ui.base.ErrorUiState

data class ListsUiState(
    val movieLists: List<ListMovieUiState> = emptyList(),
    val isLoading: Boolean = true,
    val error: ErrorUiState? = null,
){
    val isFailure: Boolean = error != null
}
