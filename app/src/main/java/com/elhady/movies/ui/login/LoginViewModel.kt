package com.elhady.movies.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.login.LoginWithUsernameAndPasswordUseCase
import com.elhady.movies.domain.usecases.login.ValidateFieldUseCase
import com.elhady.movies.domain.usecases.login.ValidatePasswordUseCase
import com.elhady.movies.domain.usecases.login.ValidateUsernameFieldUseCase
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {

    val args = LoginFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginUiEvent = MutableStateFlow<Event<LoginUiEvent?>>(Event(null))
    val loginUiEvent = _loginUiEvent.asStateFlow()

    fun onUserNameInputChange(username: CharSequence) {
        val usernameFieldState = validateUsernameFieldUseCase(username.toString())
        _loginUiState.update {
            it.copy(
                userName = username.toString(),
                userNameHelperText = usernameFieldState.errorMessage() ?: "",
                isValidForm = validateFieldUseCase(
                    loginUiState.value.userName,
                    loginUiState.value.password
                )
            )
        }
    }

    fun onPasswordInputChange(password: CharSequence) {
        val passwordFieldState = validatePasswordUseCase(password.toString())
        _loginUiState.update {
            it.copy(
                password = password.toString(),
                passwordHelperText = passwordFieldState.errorMessage() ?: "",
                isValidForm = validateFieldUseCase(
                    loginUiState.value.userName,
                    loginUiState.value.password
                )
            )
        }
    }

    fun login(){
        viewModelScope.launch {
            try {
                _loginUiState.update {
                    it.copy(isLoading = true)
                }
                val loginState = loginWithUsernameAndPasswordUseCase(
                    loginUiState.value.userName,
                    loginUiState.value.password
                )
                if (loginState) {
                   onLoginSuccess()
                }
            }catch (e: Exception){
                onLoginError(e.message.toString())
            }
        }
    }

    fun onClickSignUp(){
        _loginUiEvent.update {
            Event(LoginUiEvent.SignUpEvent)
        }
    }
    private fun onLoginSuccess(){
        _loginUiState.update { it.copy(isLoading = false) }
        _loginUiEvent.update { Event(LoginUiEvent.LoginEvent(args.form))}
        resetForm()
    }
    private fun onLoginError(message: String) {
        _loginUiState.update {
            it.copy(
                isLoading = false,
                error = message,
                passwordHelperText = message
            )
        }
    }

    private fun resetForm() {
        _loginUiState.update { it.copy(userName = "", password = "") }
    }
}