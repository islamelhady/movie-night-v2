package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.core.ui.base.ErrorUiState

data class WatchHistoryUiState(
    val searchInput: String = "",
    val movies: List<WatchHistoryRecyclerItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
    val pendingDeletion: PendingDeletion? = null
)

data class PendingDeletion(
    val movie: MovieUiState,
    val title: String?,
    val position: Int
)
