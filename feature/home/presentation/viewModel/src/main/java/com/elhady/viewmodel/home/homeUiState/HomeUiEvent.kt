package com.elhady.viewmodel.home.homeUiState

import com.elhady.viewmodel.showMore.ShowMoreType


sealed interface HomeUiEvent {
    data class ClickMovieEvent(val id: Int) : HomeUiEvent
    data class ClickActorEvent(val id: Int): HomeUiEvent
    data class ClickShowMoreEvent(val mediaType: ShowMoreType): HomeUiEvent
    data class ClickTVShowEvent(val id: Int): HomeUiEvent
}