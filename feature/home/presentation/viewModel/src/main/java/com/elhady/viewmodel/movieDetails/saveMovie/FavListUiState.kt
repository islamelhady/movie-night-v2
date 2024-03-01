package com.elhady.viewmodel.movieDetails.saveMovie


data class FavListUiState(
    val myListItemUI: List<FavListItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: List<String> = emptyList()
)

