package com.elhady.viewmodel.home.homeUiState


sealed interface HomeUiEvent {
    data class ClickMovieEvent(val movieID: Int) : HomeUiEvent
    data class ClickSeeAllMoviesEvent(val mediaType: SeeAllType) : HomeUiEvent
    object ClickSeeAllActorsEvent : HomeUiEvent
    data class ClickActorEvent(val actorID: Int): HomeUiEvent
    data class ClickSeeAllSeriesEvent(val mediaType: SeeAllType): HomeUiEvent
    data class ClickSeriesEvent(val mediaID: Int): HomeUiEvent
}