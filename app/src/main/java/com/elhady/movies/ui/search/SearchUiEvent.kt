package com.elhady.movies.ui.search

sealed interface SearchUiEvent{
    object ClickBackEvent: SearchUiEvent
    object ClickRetryEvent: SearchUiEvent
    data class ClickActorEvent(val actorId: Int): SearchUiEvent
}