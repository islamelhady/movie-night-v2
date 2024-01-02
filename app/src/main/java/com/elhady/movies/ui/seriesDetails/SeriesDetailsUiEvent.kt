package com.elhady.movies.ui.seriesDetails

import com.elhady.movies.domain.models.RatingStatus

sealed interface SeriesDetailsUiEvent {
    data class ClickSeasonEvent(val seasonNumber: Int): SeriesDetailsUiEvent
    object ClickBackButtonEvent: SeriesDetailsUiEvent
    object ClickPlayTrailerEvent: SeriesDetailsUiEvent
    data class ClickCastEvent(val castId: Int): SeriesDetailsUiEvent
    data class ClickSimilarSeriesEvent(val seriesId: Int): SeriesDetailsUiEvent
    object ClickViewReviews: SeriesDetailsUiEvent
    data class MessageAppear(val message: String): SeriesDetailsUiEvent

}