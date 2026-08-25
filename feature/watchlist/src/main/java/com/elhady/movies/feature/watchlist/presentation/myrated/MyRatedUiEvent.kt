package com.elhady.movies.feature.watchlist.presentation.myrated


sealed interface MyRatedUiEvent {

    object BackClicked : MyRatedUiEvent

    object MoviesSelected : MyRatedUiEvent

    object TvShowsSelected : MyRatedUiEvent

    data class MediaClicked(
        val id: Int
    ) : MyRatedUiEvent

    object RetryClicked : MyRatedUiEvent
}
