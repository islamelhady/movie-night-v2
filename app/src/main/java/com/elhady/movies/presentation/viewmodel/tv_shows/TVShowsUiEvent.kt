package com.elhady.movies.presentation.viewmodel.tv_shows

sealed interface TVShowsUiEvent {
    data class NavigateToTVShowDetails(val tvId: Int) : TVShowsUiEvent
    object ShowOnTheAirTVShowsResult : TVShowsUiEvent
    object ShowAiringTodayTVShowsResult : TVShowsUiEvent
    object ShowTopRatedTVShowsResult : TVShowsUiEvent
    object ShowPopularTVShowsResult : TVShowsUiEvent
    data class ShowSnackBar(val messages: String) : TVShowsUiEvent
    object ScrollToTopRecycler : TVShowsUiEvent
}