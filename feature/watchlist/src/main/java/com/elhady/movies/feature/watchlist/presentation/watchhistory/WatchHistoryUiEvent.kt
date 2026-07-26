package com.elhady.movies.feature.watchlist.presentation.watchhistory

sealed interface WatchHistoryUiEvent {
    data class NavigateToMovieDetails(val movieId: Int) : WatchHistoryUiEvent
    object ShowDeleteSnackBar : WatchHistoryUiEvent
    object OnClickBack: WatchHistoryUiEvent
    data class Error(val message: String) : WatchHistoryUiEvent
}
