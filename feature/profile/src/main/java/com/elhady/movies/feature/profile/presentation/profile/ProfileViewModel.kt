package com.elhady.movies.feature.profile.presentation.profile

import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.auth.GetAccountDetailsUseCase
import com.elhady.movies.core.domain.usecase.auth.LogoutUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.profile.presentation.profile.mapper.ProfileUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val profileUiMapper: ProfileUiMapper,
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase,
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(ProfileUiState()) {

    init {
        checkUserLoggedIn()
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.FavoriteClicked -> {
                sendEffect(ProfileUiEffect.NavigateToFavoriteScreen)
            }

            ProfileUiEvent.WatchlistClicked -> {
                sendEffect(ProfileUiEffect.NavigateToWatchlistScreen)
            }

            ProfileUiEvent.WatchHistoryClicked -> {
                sendEffect(ProfileUiEffect.NavigateToWatchHistoryScreen)
            }

            ProfileUiEvent.MyListsClicked -> {
                sendEffect(ProfileUiEffect.NavigateToMyListsScreen)
            }

            ProfileUiEvent.LogoutClicked -> {
                sendEffect(ProfileUiEffect.ShowLogoutDialog)
            }

            ProfileUiEvent.LoginClicked -> {
                sendEffect(ProfileUiEffect.NavigateToLogin)
            }

            ProfileUiEvent.LogoutConfirmed -> {
                logout()
            }

            ProfileUiEvent.RetryClicked -> {
                checkUserLoggedIn()
            }
        }
    }

    private fun checkUserLoggedIn() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { checkIsUserLoggedInUseCase() },
            onSuccess = { isLoggedIn ->
                onSuccessCheckedLoggedIn(isLoggedIn)
            },
            onError = ::onCheckLoginError
        )
    }

    private fun onSuccessCheckedLoggedIn(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            _state.update { it.copy(isLogIn = true) }
            getAccountDetails()
        } else {
            _state.update { it.copy(isLogIn = false, isLoading = false) }
        }
    }

    private fun getAccountDetails() {
        tryToExecute(
            call = {
                getAccountDetailsUseCase()
            },
            mapper = profileUiMapper,
            onSuccess = ::onAccountDetailsSuccess,
            onError = ::onAccountDetailsError,
        )
    }

    private fun onAccountDetailsSuccess(
        profileState: ProfileUiState,
    ) {
        _state.update {
            it.copy(
                username = profileState.username,
                avatarUrl = profileState.avatarUrl,
                isLogIn = true,
                isLoading = false,
                errors = null,
            )
        }
    }

    private fun onAccountDetailsError(
        appException: AppException
    ) {

        if (appException is AppException.Unauthorized) {
            logout()
            return
        }

        _state.update {
            it.copy(
                isLoading = false,
                errors = appException.toErrorUiState(),
            )
        }

        sendEffect(
            ProfileUiEffect.ShowSnackBar(
                appException.message ?: "Failed to load profile"
            )
        )
    }

    private fun onCheckLoginError(
        exception: AppException,
    ) {
        _state.update {
            it.copy(
                isLoading = false,
                isLogIn = false,
                errors = exception.toErrorUiState(),
            )
        }
    }
    private fun logout() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { logoutUseCase() },
            onSuccess = {
                _state.update { it.copy(isLogIn = false, errors = null, isLoading = false) }
                sendEffect(ProfileUiEffect.NavigateToLogin)
            },
            onError = { exception ->
                _state.update { it.copy(isLoading = false, errors = exception.toErrorUiState()) }
            }
        )
    }
}