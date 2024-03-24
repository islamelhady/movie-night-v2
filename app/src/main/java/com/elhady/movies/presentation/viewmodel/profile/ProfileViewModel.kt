package com.elhady.movies.presentation.viewmodel.profile

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.bases.NavigationRes
import com.elhady.movies.core.domain.entities.ProfileEntity
import com.elhady.movies.core.domain.usecase.usecase.profile.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.usecase.profile.GetAccountDetailsUseCase
import com.elhady.movies.core.domain.usecase.usecase.profile.LogoutUseCase
import com.elhady.movies.presentation.viewmodel.login.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val profileUiMapper: ProfileUiMapper,
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase,
    private val navigationRes: NavigationRes
) : BaseViewModel<ProfileUIState, ProfileUiEvent>(ProfileUIState()), ProfileListener {

    init {
        getAccountDetails()
        viewModelScope.launch { state.value.log() }
    }

    private fun getAccountDetails() {
        tryToExecute(
            call = {getAccountDetailsUseCase()},
            onSuccess = ::onSuccessGetAccountDetails,
            mapper = profileUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessGetAccountDetails(profileEntity: ProfileUIState){
        _state.update {
            it.copy(
                username = profileEntity.username,
                avatarUrl = profileEntity.avatarUrl,
                error = null,
                isLogIn = true,
                isLoading = false
            )
        }
    }

    private fun onError(throwable: Throwable) {
        val errors = throwable.message ?: "no internet connection"
        _state.update { it.copy(error = listOf(errors), isLoading = false) }
    }

    override fun onClickFavorite() {
        sendEvent(ProfileUiEvent.NavigateToFavoriteScreen)
    }

    override fun onClickWatchlist() {
        sendEvent(ProfileUiEvent.NavigateToWatchlistScreen)
    }

    override fun onClickWatchHistory() {
        sendEvent(ProfileUiEvent.NavigateToWatchHistoryScreen)
    }

    override fun onClickMyLists() {
        sendEvent(ProfileUiEvent.NavigateToMyListsScreen)
    }

    override fun onClickLogout() {
        sendEvent(ProfileUiEvent.Logout)
    }

    fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLogIn = false) }
            logoutUseCase()
        }
    }

    override fun onUserNotLoggedIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLogIn = true) }
            if (checkIsUserLoggedInUseCase()) {
                _state.update {
                    it.copy(isLogIn = false)
                }
            }

        }
    }

    override fun ocClickLogIn() {
        sendEvent(ProfileUiEvent.NavigateWithLink(navigationRes.authFeatureLink))
    }
}