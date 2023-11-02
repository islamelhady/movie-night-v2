package com.elhady.movies.ui.seriesDetails

import com.elhady.movies.ui.models.ActorUiState

data class SeriesDetailsUiState(
    val seriesItems: List<SeriesItems> = listOf(),
    val seriesDetailsResult: SeriesDetailsResultUiState = SeriesDetailsResultUiState(),
    val seriesCastResult: List<ActorUiState> = mutableListOf()
)