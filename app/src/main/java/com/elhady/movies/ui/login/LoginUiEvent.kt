package com.elhady.movies.ui.login

sealed interface LoginUiEvent {
    data class LoginEvent(val login: Int) : LoginUiEvent
    object SignUpEvent : LoginUiEvent
}