package com.elhady.viewmodel

sealed interface LoginUiEvent {
    data class LoginEvent(val login: Int) : LoginUiEvent
    object SignUpEvent : LoginUiEvent
    data class ShowSnackBar(val message: String): LoginUiEvent
}