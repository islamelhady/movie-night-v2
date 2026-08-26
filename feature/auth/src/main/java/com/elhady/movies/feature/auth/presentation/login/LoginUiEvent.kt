package com.elhady.movies.feature.auth.presentation.login

sealed interface LoginUiEvent {
    data class UsernameChanged(
        val username: String
    ) : LoginUiEvent

    data class PasswordChanged(
        val password: String
    ) : LoginUiEvent

    object LoginClicked : LoginUiEvent

    object SignUpClicked : LoginUiEvent
}
