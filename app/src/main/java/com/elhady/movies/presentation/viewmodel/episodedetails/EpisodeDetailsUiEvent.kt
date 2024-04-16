package com.elhady.movies.presentation.viewmodel.episodedetails

sealed interface EpisodeDetailsUiEvent {
    object ClickToBack : EpisodeDetailsUiEvent
    data class ClickToRate(val episodeId: Int) : EpisodeDetailsUiEvent
    data class ClickCast(val itemId: Int) : EpisodeDetailsUiEvent
    data class SubmitRating(val message: String) : EpisodeDetailsUiEvent
    data class ClickToPlayFullScreen(val videoKey:String) : EpisodeDetailsUiEvent
}