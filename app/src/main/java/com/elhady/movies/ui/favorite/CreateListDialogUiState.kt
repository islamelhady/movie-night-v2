package com.elhady.movies.ui.favorite

data class CreateListDialogUiState(
    val listName: String = "",
    val onErrors: List<String> = emptyList()
)