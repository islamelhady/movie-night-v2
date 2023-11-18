package com.elhady.movies.ui.favorite

sealed interface FavouriteUiEvent {
    object CLickAddEvent : FavouriteUiEvent
    object ClickCreateEvent : FavouriteUiEvent
}