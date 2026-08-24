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

    object UndoDeleteClicked : WatchHistoryUiEvent

    object DeleteSnackBarDismissed : WatchHistoryUiEvent

    object RetryClicked : WatchHistoryUiEvent

    object BackClicked : WatchHistoryUiEvent
}
