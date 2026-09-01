package com.elhady.movies.feature.details.presentation.episodedetails

import com.elhady.movies.core.ui.base.UiText

sealed interface EpisodeDetailsUiEffect {

    object NavigateBack : EpisodeDetailsUiEffect

    data class NavigateToCastDetails(
        val personId: Int,
    ) : EpisodeDetailsUiEffect

    object ShowRatingBottomSheet : EpisodeDetailsUiEffect

    data class ShowSnackBar(
        val message: UiText,
    ) : EpisodeDetailsUiEffect
}