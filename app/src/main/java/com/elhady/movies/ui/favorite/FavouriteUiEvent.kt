package com.elhady.movies.ui.favorite

sealed interface FavouriteUiEvent {
    object CLickAddEvent : FavouriteUiEvent
    object ClickCreateEvent : FavouriteUiEvent
    data class ClickSelectedItemEvent(val id: Int, val listName: String): FavouriteUiEvent
    object ClickLogin: FavouriteUiEvent
    data class ShowSnackBar(val message: String): FavouriteUiEvent
}