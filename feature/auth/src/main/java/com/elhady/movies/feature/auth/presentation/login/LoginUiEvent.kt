package com.elhady.movies.feature.auth.presentation.login

sealed interface LoginUiEvent {

    data class NavigateToHomeScreen(val id: Int) : LoginUiEvent

    data class ShowSnackBar(val message: String) : LoginUiEvent

    object SignUpEvent : LoginUiEvent
}
