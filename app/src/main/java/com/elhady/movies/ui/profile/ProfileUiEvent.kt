package com.elhady.movies.ui.profile

sealed interface ProfileUiEvent {
    object WatchHistoryEvent : ProfileUiEvent
    object RatedMoviesEvent : ProfileUiEvent
    object DialogLogoutEvent : ProfileUiEvent
}
