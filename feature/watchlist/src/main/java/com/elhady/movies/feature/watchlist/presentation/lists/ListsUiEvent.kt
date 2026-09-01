package com.elhady.movies.feature.watchlist.presentation.lists

import com.elhady.movies.core.common.MediaType

sealed interface ListsUiEvent {

    data class ListClicked(
        val listId: Int,
        val listType: MediaType,
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
