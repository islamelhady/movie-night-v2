package com.elhady.movies.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.login.LoginWithUsernameAndPasswordUseCase
import com.elhady.movies.domain.usecases.login.ValidateFieldUseCase
import com.elhady.movies.domain.usecases.login.ValidatePasswordUseCase
import com.elhady.movies.domain.usecases.login.ValidateUsernameFieldUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val validateFieldUseCase: ValidateFieldUseCase,
    private val validateUsernameFieldUseCase: ValidateUsernameFieldUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState()) {

    val args = LoginFragmentArgs.fromSavedStateHandle(savedStateHandle)

    override fun getData() {
        TODO("Not yet implemented")
    }

    fun onUserNameInputChange(username: CharSequence) {
        val usernameFieldState = validateUsernameFieldUseCase(username.toString())
        _state.update {
            it.copy(
                userName = username.toString(),
                userNameHelperText = usernameFieldState.errorMessage() ?: "",
                isValidForm = validateFieldUseCase(
                    state.value.userName,
                    state.value.password
                )
            )
        }
    }

    fun onPasswordInputChange(password: CharSequence) {
        val passwordFieldState = validatePasswordUseCase(password.toString())
        _state.update {
            it.copy(
                password = password.toString(),
                passwordHelperText = passwordFieldState.errorMessage() ?: "",
                isValidForm = validateFieldUseCase(
                    state.value.userName,
                    state.value.password
                )
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(isLoading = true)
                }
                val loginState = loginWithUsernameAndPasswordUseCase(
                    state.value.userName,
                    state.value.password
                )
                if (loginState) {
                    onLoginSuccess()
                }
            } catch (e: Exception) {
                onLoginError(e.message.toString())
            }
        }
    }

    fun onClickSignUp() {
        Event(LoginUiEvent.SignUpEvent)
    }

    private fun onLoginSuccess() {
        _state.update { it.copy(isLoading = false) }
        Event(LoginUiEvent.LoginEvent(args.form))
        resetForm()
    }

    private fun onLoginError(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                error = message,
                passwordHelperText = message
            )
        }
    }

    private fun resetForm() {
        _state.update { it.copy(userName = "", password = "") }
    }
}