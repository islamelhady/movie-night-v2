package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.feature.watchlist.presentation.ui.watchhistory.WatchHistoryRecyclerItem


data class WatchHistoryUiState(
    val searchInput: String = "",
    val movies: List<WatchHistoryRecyclerItem> = emptyList(),
    val isLoading: Boolean = false,
    val deletedMovie: MovieUiState? = null,
    val deletedTitle: String? = null,
    val snackBarUndoPressed: Boolean? = null,
    val swipePosition: Int? = null
)
