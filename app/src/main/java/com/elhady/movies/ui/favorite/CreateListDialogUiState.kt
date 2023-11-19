package com.elhady.movies.ui.favorite

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class CreateListDialogUiState(
    val listName: String = "",
    val error: List<ErrorUiState> = emptyList()
)