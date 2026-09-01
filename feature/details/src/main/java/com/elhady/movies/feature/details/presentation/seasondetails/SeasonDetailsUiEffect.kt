package com.elhady.movies.feature.details.presentation.seasondetails

import com.elhady.movies.core.ui.base.UiText

sealed interface SeasonDetailsUiEffect {

    object NavigateBack : SeasonDetailsUiEffect

    data class NavigateToEpisodeDetails(
        val episodeId: Int,
        val seriesId: Int,
        val seasonNumber: Int,
    ) : SeasonDetailsUiEffect

    data class ShowSnackBar(
        val message: UiText,
    ) : SeasonDetailsUiEffect
}