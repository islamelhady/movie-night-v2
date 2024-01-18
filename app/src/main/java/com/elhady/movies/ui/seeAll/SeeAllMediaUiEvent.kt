package com.elhady.movies.ui.seeAll

sealed interface SeeAllMediaUiEvent {
    data class ClickMovieEvent(val mediaId: Int): SeeAllMediaUiEvent
    data class ClickSeriesEvent(val mediaId: Int): SeeAllMediaUiEvent
    data class ShowSnackBar(val message: String): SeeAllMediaUiEvent
}