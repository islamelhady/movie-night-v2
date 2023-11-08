package com.elhady.movies.ui.search

sealed interface SearchUiEvent{
    object ClickRetryEvent: SearchUiEvent
}