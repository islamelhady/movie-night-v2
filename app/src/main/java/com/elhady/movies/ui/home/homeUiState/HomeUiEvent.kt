package com.elhady.movies.ui.home.homeUiState

sealed interface HomeUiEvent{
    data class ClickMovieEvent(val movieID: Int): HomeUiEvent
}