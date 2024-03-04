package com.elhady.viewmodel.home.homeUiState

import com.elhady.usecase.seeAllMedia.ShowMoreType


sealed interface HomeUiEvent {
    data class ClickMovieEvent(val id: Int) : HomeUiEvent
    data class ClickShowMoreMoviesEvent(val mediaType: ShowMoreType) : HomeUiEvent
    data class ClickShowMoreActorsEvent(val mediaType: ShowMoreType) : HomeUiEvent
    data class ClickActorEvent(val id: Int): HomeUiEvent
    data class ClickShowMoreTVShowEvent(val mediaType: ShowMoreType): HomeUiEvent
    data class ClickTVShowEvent(val id: Int): HomeUiEvent
}