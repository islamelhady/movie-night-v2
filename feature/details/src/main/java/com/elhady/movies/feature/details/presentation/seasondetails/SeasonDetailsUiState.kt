package com.elhady.movies.feature.details.presentation.seasondetails

import com.elhady.movies.feature.details.presentation.episodedetails.EpisodeHorizontalUiState

data class SeasonDetailsUiState(
    val id : Int = 0,
    val name : String = "",
    val overview : String = "",
    val episodes : List<EpisodeHorizontalUiState> = emptyList(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
)
