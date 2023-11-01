package com.elhady.movies.ui.tvShowDetails

data class TVShowDetailsUiState(
    val tvShowDetails: List<TVShowItems> = listOf(),
    val tvShowDetailsResult: TVShowDetailsResultUiState = TVShowDetailsResultUiState()
)