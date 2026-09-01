package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.UiText

data class WatchHistoryUiState(
    val searchInput: String = "",
    val movies: List<WatchHistoryRecyclerItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
    val pendingDeletion: PendingDeletion? = null
) {
    val isFailure: Boolean get() = error != null
}

data class PendingDeletion(
    val movie: MovieUiState,
    val title: UiText?,
    val position: Int
)
