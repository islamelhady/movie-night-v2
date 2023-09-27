package com.elhady.movies.ui.login

sealed interface LoginUiEvent {
    object LoginEvent : LoginUiEvent
    object SignUpEvent : LoginUiEvent
}