package com.elhady.movies.ui.profile

import com.elhady.movies.domain.usecases.CheckIfLoggedInUseCase
import com.elhady.movies.domain.usecases.GetAccountDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val accountUiStateMapper: AccountUiStateMapper
) : BaseViewModel<ProfileUiState, ProfileUiEvent>(ProfileUiState()) {

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        if (checkIfLoggedInUseCase()) {
            profile()
        } else {
            _state.update {
                it.copy(isLoggedIn = false, isLoading = false, onErrors = emptyList())
            }
        }
    }

    private fun profile(){
        tryToExecute(
            call = { getAccountDetailsUseCase() },
            onSuccess = ::onSuccessProfile,
            mapper = accountUiStateMapper,
            onError = ::onError
        )
    }

    private fun onSuccessProfile(profileUiState: ProfileUiState){
        _state.update { it.copy(isLoading = false, isLoggedIn = true, avatarPath = profileUiState.avatarPath, name = profileUiState.name, username = profileUiState.username, onErrors = emptyList()) }
    }

    private fun onError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, onErrors = listOf(throwable.message.toString())) }
    }

    fun onClickRatedMovies() {
        sendEvent(ProfileUiEvent.RatedMoviesEvent)
    }

    fun onClickLogout() {
        sendEvent(ProfileUiEvent.DialogLogoutEvent)
    }

    fun onClickWatchHistory() {
        sendEvent(ProfileUiEvent.WatchHistoryEvent)
    }

    fun onClickLogin() {
        sendEvent(ProfileUiEvent.LoginEvent)
    }


}
