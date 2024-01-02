package com.elhady.movies.ui.favorite.details

data class ListDetailsUIState(
    val savedMedia: List<FavMediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val onErrors: List<String> = emptyList()
)