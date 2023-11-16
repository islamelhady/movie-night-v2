package com.elhady.movies.ui.profile.watchHistory

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class WatchHistoryUiState(
    val allMedia: List<MediaHistoryUiState> = emptyList(),
    val error: List<ErrorUiState> = emptyList()
)