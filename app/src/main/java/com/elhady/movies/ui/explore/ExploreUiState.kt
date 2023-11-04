package com.elhady.movies.ui.explore

data class ExploreUiState(
    val trendMediaResult: List<TrendingMediaUiState> = emptyList(),
    val isLoading: Boolean = false
)
