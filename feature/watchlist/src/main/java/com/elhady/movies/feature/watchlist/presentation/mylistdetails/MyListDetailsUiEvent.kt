package com.elhady.movies.feature.watchlist.presentation.mylistdetails

sealed interface MyListDetailsUiEvent {

    data class MovieClicked(
        val movieId: Int,
    ) : MyListDetailsUiEvent

    data class TvShowClicked(
        val tvShowId: Int,
    ) : MyListDetailsUiEvent

    data class DeleteMovieClicked(
        val position: Int,
    ) : MyListDetailsUiEvent

    object BackClicked : MyListDetailsUiEvent

    object RetryClicked : MyListDetailsUiEvent
}
