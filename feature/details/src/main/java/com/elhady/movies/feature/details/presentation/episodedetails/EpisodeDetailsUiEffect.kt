package com.elhady.movies.feature.details.presentation.episodedetails

sealed interface EpisodeDetailsUiEffect {

    object NavigateBack : EpisodeDetailsUiEffect

    data class NavigateToCastDetails(
        val personId: Int,
    ) : EpisodeDetailsUiEffect

    data class NavigateToTrailer(
        val videoKey: String,
    ) : EpisodeDetailsUiEffect

    object ShowRatingBottomSheet : EpisodeDetailsUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : EpisodeDetailsUiEffect
}