package com.elhady.movies.presentation.viewmodel.explore

sealed interface ExploreUiEvent {
    object NavigateToSearchEvent: ExploreUiEvent
    data class ShowSnackBarMessageEvent(val message: String): ExploreUiEvent
    data class NavigateToMovieDetailsEvent(val movieId: Int): ExploreUiEvent
}