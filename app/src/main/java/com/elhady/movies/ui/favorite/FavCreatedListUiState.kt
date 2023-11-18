package com.elhady.movies.ui.favorite

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class FavCreatedListUiState(
    val createdList: List<CreatedListUiState> = emptyList(),
    val dialogUiState: CreateListDialogUiState = CreateListDialogUiState(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)