package com.elhady.viewmodel.favorite.details

sealed interface ListDetailsUiEvent {
    data class OnItemSelected(val savedMediaUiState: FavMediaUiState) : ListDetailsUiEvent
}