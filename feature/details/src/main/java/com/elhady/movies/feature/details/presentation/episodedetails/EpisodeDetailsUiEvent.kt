package com.elhady.movies.feature.details.presentation.episodedetails

sealed interface EpisodeDetailsUiEvent {

    object BackClicked : EpisodeDetailsUiEvent

    object RateClicked : EpisodeDetailsUiEvent

    data class CastClicked(
        val personId: Int,
    ) : EpisodeDetailsUiEvent

    data class PlayFullScreenClicked(
        val videoKey: String,
    ) : EpisodeDetailsUiEvent

    data class RatingChanged(
        val rating: Float,
    ) : EpisodeDetailsUiEvent

    object SubmitRating : EpisodeDetailsUiEvent

    object RetryClicked : EpisodeDetailsUiEvent
    object Refresh : EpisodeDetailsUiEvent
    object DismissPlayerClicked : EpisodeDetailsUiEvent
}
