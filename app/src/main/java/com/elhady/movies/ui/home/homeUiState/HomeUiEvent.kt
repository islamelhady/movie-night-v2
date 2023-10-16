package com.elhady.movies.ui.home.homeUiState

import com.elhady.movies.domain.enums.AllMediaType

sealed interface HomeUiEvent{
    data class ClickMovieEvent(val movieID: Int): HomeUiEvent
    data class ClickSeeAllMoviesEvent(val mediaType: AllMediaType): HomeUiEvent
}