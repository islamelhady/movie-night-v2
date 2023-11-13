package com.elhady.movies.ui.search

import com.elhady.movies.ui.models.MediaUiState

sealed interface SearchUiEvent{
    object ClickBackEvent: SearchUiEvent
    data class ClickActorEvent(val actorId: Int): SearchUiEvent
    data class ClickMediaEvent(val mediaUiState: MediaUiState): SearchUiEvent
}