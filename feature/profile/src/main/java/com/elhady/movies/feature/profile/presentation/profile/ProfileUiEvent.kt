package com.elhady.movies.feature.profile.presentation.profile

sealed interface ProfileUiEvent {
    object FavoriteClicked : ProfileUiEvent
    object WatchlistClicked : ProfileUiEvent
    object WatchHistoryClicked : ProfileUiEvent
    object MyListsClicked : ProfileUiEvent
    object LogoutClicked : ProfileUiEvent
    object LoginClicked : ProfileUiEvent
    object LogoutConfirmed : ProfileUiEvent
    object RetryClicked : ProfileUiEvent
    data class ThemeChanged(val isDark: Boolean) : ProfileUiEvent
}
