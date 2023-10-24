package com.elhady.movies.ui.actorDetails

sealed interface ActorDetailsUiEvent{
    data class ClickMovieEvent(val movieID: Int): ActorDetailsUiEvent
    object ClickSeeAllEvent : ActorDetailsUiEvent
    object ClickBackButton: ActorDetailsUiEvent
}