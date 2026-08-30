package com.elhady.movies.core.ui.state

data class SaveToListsUiState(
    val isFavouriteSelected: Boolean = false,
    val isWatchlistSelected: Boolean = false,
    val selectedUserLists: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val isCreateListVisible: Boolean = false
)
