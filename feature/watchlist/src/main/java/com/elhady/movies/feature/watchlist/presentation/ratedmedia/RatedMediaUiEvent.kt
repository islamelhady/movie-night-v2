package com.elhady.movies.feature.watchlist.presentation.ratedmedia


sealed interface RatedMediaUiEvent {

    object BackClicked : RatedMediaUiEvent

    object MoviesSelected : RatedMediaUiEvent

    object TvShowsSelected : RatedMediaUiEvent

    data class MediaClicked(
        val id: Int
    ) : RatedMediaUiEvent

    object RetryClicked : RatedMediaUiEvent
}
