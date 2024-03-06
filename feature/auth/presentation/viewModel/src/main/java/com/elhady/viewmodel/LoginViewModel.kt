package com.elhady.viewmodel

import androidx.lifecycle.viewModelScope
import com.elhady.base.BaseViewModel
import com.elhady.base.NavigationRes
import com.elhady.base.StringsRes
import com.elhady.usecase.LoginError
import com.elhady.usecase.LoginWithUsernameAndPasswordUseCase
import com.elhady.usecase.ValidateFieldUseCase
import com.elhady.usecase.ValidatePasswordUseCase
import com.elhady.usecase.ValidateUsernameFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val validateFieldUseCase: ValidateFieldUseCase,
    private val validateUsernameFieldUseCase: ValidateUsernameFieldUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val navigationRes: NavigationRes,
    private val stringsRes: StringsRes
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState()) {


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
                val userName = _state.value.userName
                val password = _state.value.password
                _state.update { it.copy(isLoading = true) }
                val loginState = loginWithUsernameAndPasswordUseCase(userName, password)
                when (loginState) {
                    LoginError.SUCCESS -> onLoginSuccess()
                    LoginError.REQUEST_ERROR -> onLoginFailure()
                }
        }
    }

    fun onClickSignUp() {
        sendEvent(LoginUiEvent.SignUpEvent)
    }

    private fun onLoginSuccess() {
        _state.update { it.copy(isLoading = false) }
        sendEvent(LoginUiEvent.LoginEvent(navigationRes.profileFeatureLink))
        resetForm()
    }

    private fun onLoginFailure(){
        _state.update { it.copy(isLoading = false) }
        sendEvent(LoginUiEvent.ShowSnackBar(stringsRes.someThingError))
    }

    private fun resetForm() {
        _state.update { it.copy(userName = "", password = "") }
    }
}