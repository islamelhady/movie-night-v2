package com.elhady.movies.ui.profile

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.CheckIfLoggedInUseCase
import com.elhady.movies.domain.usecases.GetAccountDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val accountUiStateMapper: AccountUiStateMapper
) : BaseViewModel<ProfileUiState>(ProfileUiState()) {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private val _profileUIEvent=MutableStateFlow<Event<ProfileUiEvent>?>(null)
    val profileUIEvent= _profileUIEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        if (checkIfLoggedInUseCase()) {
            _profileUiState.update {
                it.copy(isLoading = true, isLoggedIn = true, error = false)
            }

            viewModelScope.launch {
                val accountResult = accountUiStateMapper.map(getAccountDetailsUseCase())
                _profileUiState.update {
                    it.copy(
                        avatarPath = accountResult.avatarPath,
                        name = accountResult.name,
                        username = accountResult.username,
                        isLoading = false
                    )
                }
            }
        } else {
            _profileUiState.update {
                it.copy(isLoggedIn = false)
            }
        }
    }

    fun onClickRatedMovies() {
        _profileUIEvent.update { Event(ProfileUiEvent.RatedMoviesEvent) }
    }

    fun onClickLogout() {
        _profileUIEvent.update { Event(ProfileUiEvent.DialogLogoutEvent) }
    }

    fun onClickWatchHistory() {
        _profileUIEvent.update { Event(ProfileUiEvent.WatchHistoryEvent) }
    }

    fun onClickLogin(){
        _profileUIEvent.update {
            Event(ProfileUiEvent.LoginEvent)
        }
    }


}
