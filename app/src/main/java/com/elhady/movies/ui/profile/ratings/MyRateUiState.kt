package com.elhady.movies.ui.profile.ratings

data class MyRateUiState(
    val ratedList: List<RatedUiState> = emptyList(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)