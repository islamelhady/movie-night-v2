package com.elhady.viewmodel.profile.watchHistory

import com.elhady.viewmodel.profile.watchHistory.MediaHistoryUiState

data class WatchHistoryUiState(
    val allMedia: List<MediaHistoryUiState> = emptyList(),
    val onErrors: List<String> = emptyList()
)