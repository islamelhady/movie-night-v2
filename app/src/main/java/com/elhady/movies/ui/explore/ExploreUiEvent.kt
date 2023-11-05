package com.elhady.movies.ui.explore

sealed interface ExploreUiEvent {
    data class ClickTrendEvent(val trendingMediaUiState: TrendingMediaUiState) : ExploreUiEvent
    object ScrollToTopRecycler : ExploreUiEvent
}