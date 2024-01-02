package com.elhady.movies.ui.explore

data class ExploreUiState(
    val trendMediaResult: List<TrendingMediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class TrendingMediaUiState(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val data: String = "",
    val type: String = "",
    val rate: Float = 0f
)
