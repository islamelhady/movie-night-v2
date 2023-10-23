package com.elhady.movies.ui.actors

sealed interface ActorsUiEvent {
    data class ClickActorEvent(val actorID: Int) : ActorsUiEvent
}