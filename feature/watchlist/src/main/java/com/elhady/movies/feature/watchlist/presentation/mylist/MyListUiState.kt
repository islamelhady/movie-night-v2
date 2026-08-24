package com.elhady.movies.feature.watchlist.presentation.mylist

import com.elhady.movies.core.ui.base.ErrorUiState

data class MyListUiState(
    val movieLists: List<ListMovieUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
){
    val isFailure: Boolean = error != null
}
