package com.elhady.movies.ui.profile.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.LogoutUseCase
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutDialogViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) :
    ViewModel() {

    private val _logoutUiEvent = MutableStateFlow<Event<LogoutUiEvent>?>(null)
    val logoutUIEvent = _logoutUiEvent.asStateFlow()
    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
            logoutEvent()
        }
    }

    fun onCloseDialog() {
        _logoutUiEvent.update { Event(LogoutUiEvent.CloseDialogEvent) }
    }

    private fun logoutEvent() {
        _logoutUiEvent.update {
            Event(LogoutUiEvent.LogoutEvent)
        }
    }
}
