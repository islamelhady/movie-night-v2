package com.elhady.movies.feature.profile.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.ForbiddenThrowable
import com.elhady.movies.core.common.NoNetworkThrowable
import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.ui.bases.BaseViewModel
import com.elhady.movies.core.ui.listener.NavigationRes
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.auth.LogoutUseCase
import com.elhady.movies.core.domain.usecase.auth.GetAccountDetailsUseCase
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
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            if (checkIsUserLoggedInUseCase()) {
                _state.update { it.copy(isLogIn = true, isLoading = true, error = emptyList()) }
                getAccountDetails()
            }else {
                _state.update { it.copy(isLogIn = false) }
            }
        }
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
                error = emptyList(),
                isLoading = false
            )
        }
        Log.d("OnSuccessGetAccount", "${profileEntity.username}")
    }

    private fun onError(throwable: Throwable) {
        val errors = throwable.message ?: "SOME THINK WRONG"
        when (throwable) {
            is NoNetworkThrowable -> "No Network Connection"
            is UnauthorizedThrowable -> "Unauthorized"
            is ForbiddenThrowable -> "Forbidden"
            else -> throwable.message.toString()
        }
        _state.update { it.copy(error = listOf(errors), isLoading = false) }
        Log.d("onError", "${throwable.message} $errors")
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
