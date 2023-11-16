package com.elhady.movies.ui.seriesDetails

sealed interface SeriesDetailsUiEvent {
    data class ClickSeasonEvent(val seasonNumber: Int): SeriesDetailsUiEvent
    object ClickBackButtonEvent: SeriesDetailsUiEvent
    object ClickPlayTrailerEvent: SeriesDetailsUiEvent
    data class ClickCastEvent(val castId: Int): SeriesDetailsUiEvent
    data class ClickSimilarSeriesEvent(val seriesId: Int): SeriesDetailsUiEvent
    object ClickViewReviews: SeriesDetailsUiEvent
    object MessageAppear: SeriesDetailsUiEvent

}