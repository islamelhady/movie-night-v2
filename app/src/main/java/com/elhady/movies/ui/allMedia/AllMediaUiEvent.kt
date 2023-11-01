package com.elhady.movies.ui.allMedia

sealed interface AllMediaUiEvent {
    data class ClickMovieEvent(val mediaId: Int): AllMediaUiEvent
    object ClickSeriesEvent: AllMediaUiEvent
}