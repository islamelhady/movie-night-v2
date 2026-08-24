package com.elhady.movies.feature.watchlist.presentation.mylist

sealed interface MyListUiEvent {

    data class ListClicked(
        val listId: Int,
        val listType: String,
        val listName: String,
    ) : MyListUiEvent

    object NewListClicked : MyListUiEvent

    object BackClicked : MyListUiEvent

    data class DeleteClicked(
        val listId: Int,
        val listName: String,
    ) : MyListUiEvent

    data class CreateList(
        val listName: String,
    ) : MyListUiEvent

    object RetryClicked : MyListUiEvent
}
