package com.elhady.viewmodel.profile.watchHistory

sealed interface WatchHistoryUiEvent {
    data class MovieEvent(val movieId: Int) : WatchHistoryUiEvent
    data class SeriesEvent(val tvShowId: Int) : WatchHistoryUiEvent
}