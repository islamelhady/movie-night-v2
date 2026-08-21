package com.elhady.movies.feature.auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.domain.usecase.auth.LoginError
import com.elhady.movies.core.domain.usecase.auth.LoginUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.resource.NavigationRes
import com.elhady.movies.core.ui.resource.StringsRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val stringsRes: StringsRes,
    private val navigationRes: NavigationRes,
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
        viewModelScope.launch {
            val username = state.value.username
            val password = state.value.password

            _state.update {
                it.copy(
                    isLoading = true,
                    usernameError = null,
                    passwordError = null,
                )
            }

            when (loginUseCase(username, password)) {
                LoginError.USER_NAME_ERROR -> {
                    _state.update {
                        it.copy(
                            usernameError = stringsRes.usernameIsRequired,
                            isLoading = false,
                        )
                    }
                }

                LoginError.PASSWORD_ERROR -> {
                    _state.update {
                        it.copy(
                            passwordError = stringsRes.passwordIsRequired,
                            isLoading = false,
                        )
                    }
                }

                LoginError.REQUEST_ERROR -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }

                    sendEffect(
                        LoginUiEffect.ShowSnackBar(
                            stringsRes.theRequestFailed
                        )
                    )
                }

                LoginError.SUCCESS -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }

                    sendEffect(LoginUiEffect.NavigateToHome)
                }

                LoginError.NO_INPUT_ERRORS -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}