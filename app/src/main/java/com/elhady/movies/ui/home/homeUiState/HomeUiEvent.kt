package com.elhady.movies.ui.home.homeUiState

import com.elhady.movies.domain.enums.AllMediaType
import com.elhady.movies.domain.enums.SeeAllType

sealed interface HomeUiEvent {
    data class ClickMovieEvent(val movieID: Int) : HomeUiEvent
    data class ClickSeeAllMoviesEvent(val mediaType: SeeAllType) : HomeUiEvent
    object ClickSeeAllActorsEvent : HomeUiEvent
    data class ClickActorEvent(val actorID: Int): HomeUiEvent
    data class ClickSeeAllSeriesEvent(val mediaType: SeeAllType): HomeUiEvent
    data class ClickSeriesEvent(val mediaID: Int): HomeUiEvent
}