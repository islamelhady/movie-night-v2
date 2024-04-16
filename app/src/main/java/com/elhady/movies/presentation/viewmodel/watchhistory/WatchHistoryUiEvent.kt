package com.elhady.movies.presentation.viewmodel.watchhistory

sealed interface WatchHistoryUiEvent {
    data class NavigateToMovieDetails(val movieId: Int) : WatchHistoryUiEvent
    object ShowDeleteSnackBar : WatchHistoryUiEvent
    object OnClickBack: WatchHistoryUiEvent
    data class Error(val message: String) : WatchHistoryUiEvent
}