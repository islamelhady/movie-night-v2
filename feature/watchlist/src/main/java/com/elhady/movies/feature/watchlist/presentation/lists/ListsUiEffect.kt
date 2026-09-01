package com.elhady.movies.feature.watchlist.presentation.lists

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.ui.base.UiText

sealed interface ListsUiEffect {

    data class NavigateToListDetails(
        val listId: Int,
        val listType: MediaType,
        val listName: String,
    ) : ListsUiEffect

    object NavigateBack : ListsUiEffect

    object OpenCreateListBottomSheet : ListsUiEffect

    data class ShowDeleteConfirmation(
        val listId: Int,
        val listName: String,
    ) : ListsUiEffect

    data class ShowSnackBar(
        val message: UiText,
    ) : ListsUiEffect
}