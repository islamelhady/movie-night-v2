package com.elhady.movies.ui.explore

sealed interface ExploreUiEvent {
    data class ClickTrendEvent(val trendingMediaUiState: TrendingMediaUiState) : ExploreUiEvent
    object ScrollToTopRecycler : ExploreUiEvent
    object ClickMoviesEvent: ExploreUiEvent
    object ClickSeriesEvent: ExploreUiEvent
    object ClickActorsEvent: ExploreUiEvent

}