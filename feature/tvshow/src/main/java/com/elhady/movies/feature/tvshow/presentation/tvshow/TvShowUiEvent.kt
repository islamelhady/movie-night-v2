package com.elhady.movies.feature.tvshow.presentation.tvshow

sealed interface TvShowUiEvent {
    object OnTheAirTvShowClicked : TvShowUiEvent
    object AiringTodayTvShowClicked : TvShowUiEvent
    object TopRatedTvShowClicked : TvShowUiEvent
    object PopularTvShowClicked : TvShowUiEvent
    data class TvShowItemClicked(val tvId: Int) : TvShowUiEvent
    object RetryClicked : TvShowUiEvent
    object ToTopClicked : TvShowUiEvent
}
