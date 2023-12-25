package com.elhady.movies.ui.favorite

data class FavCreatedListUiState(
    val createdList: List<CreatedListUiState> = emptyList(),
    val dialogUiState: CreateListDialogUiState = CreateListDialogUiState(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val onErrors: List<String> = emptyList()
)