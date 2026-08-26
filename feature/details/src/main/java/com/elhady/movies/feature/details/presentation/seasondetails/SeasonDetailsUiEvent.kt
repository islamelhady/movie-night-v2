package com.elhady.movies.feature.details.presentation.seasondetails

sealed interface SeasonDetailsUiEvent {

    object BackClicked : SeasonDetailsUiEvent

    object RetryClicked : SeasonDetailsUiEvent

    data class EpisodeClicked(
        val episodeId: Int,
    ) : SeasonDetailsUiEvent
}
