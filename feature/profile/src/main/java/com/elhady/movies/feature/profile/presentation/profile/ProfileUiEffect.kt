package com.elhady.movies.feature.profile.presentation.profile

sealed interface ProfileUiEffect {
    object NavigateToFavoriteScreen : ProfileUiEffect
    object NavigateToWatchlistScreen : ProfileUiEffect
    object NavigateToWatchHistoryScreen : ProfileUiEffect
    object NavigateToRateScreen : ProfileUiEffect
    object NavigateToMyListsScreen : ProfileUiEffect
    object NavigateToLogin : ProfileUiEffect
    object ShowLogoutDialog : ProfileUiEffect
    data class ShowSnackBar(val message: Int) : ProfileUiEffect
}
