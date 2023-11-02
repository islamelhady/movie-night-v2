package com.elhady.movies.ui.seriesDetails

import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.ReviewUiState
import com.elhady.movies.ui.seriesDetails.seriesUiState.SeasonUiState

data class SeriesDetailsUiState(
    val seriesItems: List<SeriesItems> = mutableListOf(),
    val seriesDetailsResult: SeriesDetailsResultUiState = SeriesDetailsResultUiState(),
    val seriesCastResult: List<ActorUiState> = mutableListOf(),
    val seriesSimilarResult: List<MediaUiState> = mutableListOf(),
    val seriesSeasonsResult: List<SeasonUiState> = mutableListOf(),
    val seriesReviewResult: List<ReviewUiState> = mutableListOf()
)