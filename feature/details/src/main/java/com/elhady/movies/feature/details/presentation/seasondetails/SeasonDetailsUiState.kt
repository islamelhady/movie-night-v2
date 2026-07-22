package com.elhady.movies.feature.details.presentation.seasondetails

import com.elhady.movies.core.common.presentation.model.EpisodeHorizontalUIState

data class SeasonDetailsUiState(
    val id : Int = 0,
    val name : String = "",
    val overview : String = "",
    val episodes : List<EpisodeHorizontalUIState> = emptyList(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
)
