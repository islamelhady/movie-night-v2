package com.elhady.movies.ui.search

sealed interface SearchUiEvent{
    data class ClickInputSearch(val query: String): SearchUiEvent
}