package com.elhady.movies.feature.auth.presentation.login

interface LoginUiEffect {
    object NavigateToHome : LoginUiEffect

    object NavigateToSignUp : LoginUiEffect

    data class ShowSnackBar(
        val message: String
    ) : LoginUiEffect
}