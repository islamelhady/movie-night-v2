package com.elhady.movies.ui.seriesDetails

sealed interface SeriesDetailsUiEvent {
    data class ClickSeasonEvent(val seasonNumber: Int): SeriesDetailsUiEvent
}