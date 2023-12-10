package com.elhady.movies.ui.seriesDetails

import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.ReviewUiState

data class SeriesDetailsUiState(
    val seriesDetailsResult: SeriesDetailsResultUiState = SeriesDetailsResultUiState(),
    val seriesCastResult: List<ActorUiState> = emptyList(),
    val seriesSimilarResult: List<MediaUiState> = emptyList(),
    val seriesSeasonsResult: List<SeasonUiState> = emptyList(),
    val seriesReviewResult: List<ReviewUiState> = emptyList(),
    val ratingValue: Float = 0f,
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val onError: List<String> = emptyList()
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