package com.elhady.movies.feature.watchlist.presentation.mylist

sealed interface MyListUiEffect {

    data class NavigateToListDetails(
        val listId: Int,
        val listType: String,
        val listName: String,
    ) : MyListUiEffect

    object NavigateBack : MyListUiEffect

    object OpenCreateListBottomSheet : MyListUiEffect

    data class ShowDeleteConfirmation(
        val listId: Int,
        val listName: String,
    ) : MyListUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : MyListUiEffect
}