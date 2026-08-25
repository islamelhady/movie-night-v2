package com.elhady.movies.feature.watchlist.presentation.lists

sealed interface ListsUiEvent {

    data class ListClicked(
        val listId: Int,
        val listType: String,
        val listName: String,
    ) : ListsUiEvent

    object NewListClicked : ListsUiEvent

    object BackClicked : ListsUiEvent

    data class DeleteClicked(
        val listId: Int,
        val listName: String,
    ) : ListsUiEvent

    data class CreateList(
        val listName: String,
    ) : ListsUiEvent

    object RetryClicked : ListsUiEvent
}
