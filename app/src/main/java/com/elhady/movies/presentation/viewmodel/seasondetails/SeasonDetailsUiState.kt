package com.elhady.movies.presentation.viewmodel.seasondetails

import com.elhady.movies.presentation.viewmodel.common.model.EpisodeHorizontalUIState

data class SeasonDetailsUiState(
    val id : Int = 0,
    val name : String = "",
    val overview : String = "",
    val episodes : List<EpisodeHorizontalUIState> = emptyList(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
)
