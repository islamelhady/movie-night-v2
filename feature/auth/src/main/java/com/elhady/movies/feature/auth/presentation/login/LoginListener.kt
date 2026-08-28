package com.elhady.movies.feature.auth.presentation.login

import com.elhady.movies.core.ui.base.BaseInteractionListener

interface LoginListener : BaseInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onLoginClicked()
    fun onSignUpClicked()
}
