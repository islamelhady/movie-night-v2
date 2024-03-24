package com.elhady.movies.presentation.viewmodel.seasondetails

sealed interface SeasonDetailsUiEvent {
    data class NavigateToEpisodeDetails(val episodeId: Int, val seriesId: Int, val seasonNumber: Int) : SeasonDetailsUiEvent
    data class ShowSnackBar(val messages: String) : SeasonDetailsUiEvent
    object NavigateBack: SeasonDetailsUiEvent
}