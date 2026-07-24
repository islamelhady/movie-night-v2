package com.elhady.movies.feature.showmore.presentation

sealed interface ShowMoreUiEvent {
    data class NavigateToMovieDetailsEvent(val itemId: Int) : ShowMoreUiEvent
    data class NavigateToTvShowDetailsEvent(val itemId: Int) : ShowMoreUiEvent
    object BackNavigateEvent : ShowMoreUiEvent
    data class ShowSnackBarEvent(val messages: String) : ShowMoreUiEvent

}
