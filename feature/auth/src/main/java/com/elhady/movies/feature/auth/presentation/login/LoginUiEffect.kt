package com.elhady.movies.feature.auth.presentation.login

import com.elhady.movies.core.ui.base.UiText

interface LoginUiEffect {
    object NavigateToHome : LoginUiEffect

    object NavigateToSignUp : LoginUiEffect

    data class ShowSnackBar(
        val message: UiText
    ) : LoginUiEffect
}