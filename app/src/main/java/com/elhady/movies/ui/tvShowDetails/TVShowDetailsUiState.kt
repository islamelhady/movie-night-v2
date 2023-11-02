package com.elhady.movies.ui.tvShowDetails

import com.elhady.movies.ui.models.ActorUiState

data class TVShowDetailsUiState(
    val seriesItems: List<SeriesItems> = listOf(),
    val seriesDetailsResult: TVShowDetailsResultUiState = TVShowDetailsResultUiState(),
    val seriesCastResult: List<ActorUiState> = mutableListOf()
)