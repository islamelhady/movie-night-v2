package com.elhady.movies.ui.favorite.details

sealed interface ListDetailsUiEvent {
    data class OnItemSelected(val savedMediaUiState: FavMediaUiState) : ListDetailsUiEvent
}