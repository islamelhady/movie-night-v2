package com.elhady.movies.feature.profile.presentation

sealed interface ProfileUiEvent {
    object NavigateToFavoriteScreen : ProfileUiEvent
    object NavigateToWatchlistScreen : ProfileUiEvent
    object NavigateToWatchHistoryScreen : ProfileUiEvent
    object NavigateToMyListsScreen : ProfileUiEvent
    object Logout : ProfileUiEvent
    data class NavigateWithLink(val link: Int) : ProfileUiEvent
}
