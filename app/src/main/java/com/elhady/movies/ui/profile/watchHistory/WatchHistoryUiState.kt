package com.elhady.movies.ui.profile.watchHistory

data class WatchHistoryUiState(
    val allMedia: List<MediaHistoryUiState> = emptyList(),
    val onErrors: List<String> = emptyList()
)