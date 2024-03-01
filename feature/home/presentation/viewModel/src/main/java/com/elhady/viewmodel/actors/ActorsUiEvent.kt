package com.elhady.viewmodel.actors

sealed interface ActorsUiEvent {
    data class ClickActorEvent(val actorID: Int) : ActorsUiEvent
}