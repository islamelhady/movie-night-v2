package com.elhady.movies.feature.watchlist.presentation.listcontents

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.UiText

data class ListContentsUiState(
    val title: UiText = UiText.Dynamic(""),
    val movies: List<MovieUiState> = emptyList(),
    val isLoading: Boolean = true,
    val error: ErrorUiState? = null,
){
    val isFailure = error != null
}
