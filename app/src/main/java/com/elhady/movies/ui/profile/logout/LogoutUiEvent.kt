package com.elhady.movies.ui.profile.logout

sealed interface LogoutUiEvent {
    object LogoutEvent : LogoutUiEvent
    object CloseDialogEvent : LogoutUiEvent
}