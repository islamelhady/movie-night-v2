package com.elhady.movies.feature.explore.presentation.explore

interface ExploreUiEvent {
    object SearchClicked : ExploreUiEvent
    data class MovieClicked(val id: Int) : ExploreUiEvent
    object ChangeLayoutClicked : ExploreUiEvent
    object RetryClicked  : ExploreUiEvent
}
