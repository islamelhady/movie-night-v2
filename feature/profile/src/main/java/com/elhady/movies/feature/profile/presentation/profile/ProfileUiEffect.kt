package com.elhady.movies.feature.profile.presentation.profile

import com.elhady.movies.core.ui.base.UiText

sealed interface ProfileUiEffect {
    object NavigateToFavoriteScreen : ProfileUiEffect
    object NavigateToWatchlistScreen : ProfileUiEffect
    object NavigateToWatchHistoryScreen : ProfileUiEffect
    object NavigateToRateScreen : ProfileUiEffect
    object NavigateToMyListsScreen : ProfileUiEffect
    object NavigateToLogin : ProfileUiEffect
    object ShowLogoutDialog : ProfileUiEffect
    data class ShowSnackBar(val message: UiText) : ProfileUiEffect
}
