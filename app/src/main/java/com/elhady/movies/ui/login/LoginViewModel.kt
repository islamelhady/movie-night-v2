package com.elhady.movies.ui.login

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.elhady.movies.R
import com.elhady.movies.domain.usecases.login.LoginError
import com.elhady.movies.domain.usecases.login.LoginWithUsernameAndPasswordUseCase
import com.elhady.movies.domain.usecases.login.ValidateFieldUseCase
import com.elhady.movies.domain.usecases.login.ValidatePasswordUseCase
import com.elhady.movies.domain.usecases.login.ValidateUsernameFieldUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.utilities.FormFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithUsernameAndPasswordUseCase: LoginWithUsernameAndPasswordUseCase,
    private val validateFieldUseCase: ValidateFieldUseCase,
    private val validateUsernameFieldUseCase: ValidateUsernameFieldUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState()) {


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
        sendEvent(LoginUiEvent.LoginEvent(R.id.profileFragment))
        resetForm()
    }

    private fun onLoginFailure(){
        _state.update { it.copy(isLoading = false) }
        sendEvent(LoginUiEvent.ShowSnackBar("Failed Please Try Again Later  \uD83E\uDD14."))
    }

    private fun resetForm() {
        _state.update { it.copy(userName = "", password = "") }
    }
}