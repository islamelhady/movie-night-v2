package com.elhady.viewmodel.seriesDetails

import com.elhady.viewmodel.models.ActorUiState
import com.elhady.viewmodel.models.MediaUiState
import com.elhady.viewmodel.models.ReviewUiState

data class SeriesDetailsUiState(
    val seriesDetailsResult: SeriesDetailsResultUiState = SeriesDetailsResultUiState(),
    val seriesCastResult: List<ActorUiState> = emptyList(),
    val seriesSimilarResult: List<MediaUiState> = emptyList(),
    val seriesSeasonsResult: List<SeasonUiState> = emptyList(),
    val seriesReviewResult: List<ReviewUiState> = emptyList(),
    val ratingValue: Float = 0f,
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class SeasonUiState(
    val seasonId: Int = 0,
    val seasonName: String = "",
    val seasonPoster: String = "",
    val seasonOverview: String = "",
    val seasonYear: String = "",
    val seasonNumber: Int = 0,
    val seasonEpisodeCount: Int = 0
)