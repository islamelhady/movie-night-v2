package com.elhady.movies.presentation.viewmodel.watchhistory

import com.elhady.movies.presentation.ui.watchhistory.WatchHistoryRecyclerItem


data class WatchHistoryUiState(
    val searchInput: String = "",
    val movies: List<WatchHistoryRecyclerItem> = emptyList(),
    val isLoading: Boolean = false,
    val deletedMovie: MovieUiState? = null,
    val deletedTitle: String? = null,
    val snackBarUndoPressed: Boolean? = null,
    val swipePosition: Int? = null
)

