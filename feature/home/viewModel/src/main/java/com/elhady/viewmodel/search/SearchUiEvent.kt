package com.elhady.viewmodel.search

import com.elhady.viewmodel.models.MediaUiState

sealed interface SearchUiEvent{
    object ClickBackEvent: SearchUiEvent
    data class ClickActorEvent(val actorId: Int): SearchUiEvent
    data class ClickMediaEvent(val mediaUiState: MediaUiState): SearchUiEvent
}