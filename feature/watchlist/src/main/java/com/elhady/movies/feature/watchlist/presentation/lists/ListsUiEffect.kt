package com.elhady.movies.feature.watchlist.presentation.lists

sealed interface ListsUiEffect {

    data class NavigateToListDetails(
        val listId: Int,
        val listType: String,
        val listName: String,
    ) : ListsUiEffect

    object NavigateBack : ListsUiEffect

    object OpenCreateListBottomSheet : ListsUiEffect

    data class ShowDeleteConfirmation(
        val listId: Int,
        val listName: String,
    ) : ListsUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : ListsUiEffect
}