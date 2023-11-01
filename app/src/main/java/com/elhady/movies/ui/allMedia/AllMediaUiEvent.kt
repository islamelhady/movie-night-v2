package com.elhady.movies.ui.allMedia

sealed interface AllMediaUiEvent {
    data class ClickMediaEvent(val mediaId: Int): AllMediaUiEvent
}