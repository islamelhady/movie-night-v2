package com.elhady.movies.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
) : ViewModel() {

    val userName = MutableLiveData("")
    val password = MutableLiveData("")

    private val _passwordHelperText = MutableLiveData("")
    val passwordHelperText: LiveData<String> = _passwordHelperText

    private val _userNameHelperText = MutableLiveData("")
    val userNameHelperText: LiveData<String> = _userNameHelperText

    private val _loginRequestState = MutableLiveData<State<Boolean>>()
    val loginRequestState: LiveData<State<Boolean>> = _loginRequestState

    private val _loginEvent = MutableLiveData<Event<Boolean>>()
    val loginEvent: LiveData<Event<Boolean>> = _loginEvent




    private fun whenFormIsValid() {
        login()
    }

    fun login() {
        viewModelScope.launch {
            val login = accountRepository.loginWithUsernameAndPassword(
                userName.value.toString(),
                password.value.toString()
            )
            if(login){
                onLoginSuccessfully()
            }else{
                onLoginError("ERROR")
            }
        }
    }

    private fun onLoginSuccessfully() {
        _loginRequestState.postValue(State.Success(true))
        _loginEvent.postValue(Event(true))
        resetForm()

    }

    private fun onLoginError(message: String) {
        _loginRequestState.postValue(State.Error(message))
        _passwordHelperText.postValue(message)

    }


    private fun resetForm() {
        userName.postValue(null)
        password.postValue(null)
    }

}