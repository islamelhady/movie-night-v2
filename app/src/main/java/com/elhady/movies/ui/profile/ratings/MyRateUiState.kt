package com.elhady.movies.ui.profile.ratings

import com.elhady.movies.ui.movieDetails.ErrorUiState

data class MyRateUiState(
    val ratedList: List<RatedUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: List<ErrorUiState> = emptyList()
)