package com.elhady.movies.presentation.viewmodel.explore

sealed interface ExploreUiEvent {
    object NavigateToSearchEvent: ExploreUiEvent
}