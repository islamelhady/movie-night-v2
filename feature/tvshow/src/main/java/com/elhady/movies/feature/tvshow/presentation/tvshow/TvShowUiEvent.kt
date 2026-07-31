package com.elhady.movies.feature.tvshow.presentation.tvshow

sealed interface TvShowUiEvent {
    data class NavigateToTvShowDetails(val tvId: Int) : TvShowUiEvent
    object ShowOnTheAirTvShowsResult : TvShowUiEvent
    object ShowAiringTodayTvShowsResult : TvShowUiEvent
    object ShowTopRatedTvShowsResult : TvShowUiEvent
    object ShowPopularTvShowsResult : TvShowUiEvent
    data class ShowSnackBar(val messages: String) : TvShowUiEvent
    object ScrollToTopRecycler : TvShowUiEvent
}
