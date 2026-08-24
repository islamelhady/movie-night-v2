package com.elhady.movies.feature.watchlist.presentation.watchhistory

sealed interface WatchHistoryUiEvent {

    data class SearchQueryChanged(
        val query: String
    ) : WatchHistoryUiEvent

    data class MovieClicked(
        val movieId: Int
    ) : WatchHistoryUiEvent

    data class MovieSwiped(
        val position: Int
    ) : WatchHistoryUiEvent

    data object UndoDeleteClicked : WatchHistoryUiEvent

    data object DeleteSnackBarDismissed : WatchHistoryUiEvent

    data object RetryClicked : WatchHistoryUiEvent

    data object BackClicked : WatchHistoryUiEvent
}
