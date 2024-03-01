package com.elhady.viewmodel.profile

sealed interface ProfileUiEvent {
    object WatchHistoryEvent : ProfileUiEvent
    object RatedMoviesEvent : ProfileUiEvent
    object DialogLogoutEvent : ProfileUiEvent
    object LoginEvent: ProfileUiEvent
}
