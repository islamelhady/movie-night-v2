package com.elhady.movies.ui.movieDetails.saveMovie

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class FavListUiState(
    val myListItemUI: List<FavListItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)

