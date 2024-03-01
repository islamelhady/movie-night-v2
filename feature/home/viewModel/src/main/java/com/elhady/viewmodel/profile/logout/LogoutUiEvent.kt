package com.elhady.viewmodel.profile.logout

sealed interface LogoutUiEvent {
    object LogoutEvent : LogoutUiEvent
    object CloseDialogEvent : LogoutUiEvent
}