package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.core.ui.base.ErrorUiState

sealed interface WatchHistoryUiEffect {

    data class NavigateToMovieDetails(
        val movieId: Int
    ) : WatchHistoryUiEffect

    data object ShowDeleteSnackBar : WatchHistoryUiEffect

    data class ShowErrorSnackBar(
        val error: ErrorUiState
    ) : WatchHistoryUiEffect

    data object NavigateBack : WatchHistoryUiEffect
}