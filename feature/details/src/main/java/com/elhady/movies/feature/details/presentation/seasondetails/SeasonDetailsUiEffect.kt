package com.elhady.movies.feature.details.presentation.seasondetails

sealed interface SeasonDetailsUiEffect {

    object NavigateBack : SeasonDetailsUiEffect

    data class NavigateToEpisodeDetails(
        val episodeId: Int,
        val seriesId: Int,
        val seasonNumber: Int,
    ) : SeasonDetailsUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : SeasonDetailsUiEffect
}