package com.elhady.movies.feature.auth.presentation.login

import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.auth.LoginError
import com.elhady.movies.core.domain.usecase.auth.LoginUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.resource.StringsRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val stringsRes: StringsRes,
) : BaseViewModel<LoginUiState, LoginUiEffect>(LoginUiState()) {

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.UsernameChanged -> {
                _state.update {
                    it.copy(
                        username = event.username,
                        usernameError = null,
                    )
                }
            }

            is LoginUiEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        passwordError = null,
                    )
                }
            }

            LoginUiEvent.LoginClicked -> {
                login()
            }

            LoginUiEvent.SignUpClicked -> {
                sendEffect(LoginUiEffect.NavigateToSignUp)
            }
        }
    }

    private fun login() {
        val username = state.value.username
        val password = state.value.password

        _state.update {
            it.copy(
                isLoading = true,
                usernameError = null,
                passwordError = null,
            )
        }

        tryToExecute(
            call = { loginUseCase(username, password) },
            onSuccess = ::onLoginSuccess,
            onError = ::onLoginError
        )
    }

    private fun onLoginSuccess(loginError: LoginError) {
        _state.update { it.copy(isLoading = false) }
        when (loginError) {
            LoginError.USER_NAME_ERROR -> {
                _state.update { it.copy(usernameError = stringsRes.usernameIsRequired) }
            }

            LoginError.PASSWORD_ERROR -> {
                _state.update { it.copy(passwordError = stringsRes.passwordIsRequired) }
            }

            LoginError.SUCCESS -> {
                sendEffect(LoginUiEffect.NavigateToHome)
            }

            else -> {}
        }
    }

    private fun onLoginError(error: AppException) {
        _state.update { it.copy(isLoading = false) }
        sendEffect(LoginUiEffect.ShowSnackBar(stringsRes.theRequestFailed))
    }
}