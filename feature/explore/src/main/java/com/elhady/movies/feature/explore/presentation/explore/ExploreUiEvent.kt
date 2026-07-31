package com.elhady.movies.feature.explore.presentation.explore

sealed interface ExploreUiEvent {
    object NavigateToSearchEvent: ExploreUiEvent
    data class ShowSnackBarMessageEvent(val message: String): ExploreUiEvent
    data class NavigateToMovieDetailsEvent(val movieId: Int): ExploreUiEvent
}
