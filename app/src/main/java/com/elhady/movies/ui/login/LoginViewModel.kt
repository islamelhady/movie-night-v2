package com.elhady.movies.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elhady.movies.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository): ViewModel() {

    private val _username = MutableLiveData("")
    private val _password = MutableLiveData("")

    fun onUsernameChanged(userName: String){
        _username.postValue(userName)
    }

    fun onPasswordChanged(password: String){
        _password.postValue(password)
    }

    suspend fun loginWithUsernameAndPassword(userName: String, password: String){
        accountRepository.loginWithUsernameAndPassword(userName, password)
    }
}