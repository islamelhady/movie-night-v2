package com.elhady.movies.feature.watchlist.presentation.listcontents

sealed interface ListContentsUiEvent {

    data class MovieClicked(
        val movieId: Int,
    ) : ListContentsUiEvent

    data class TvShowClicked(
        val tvShowId: Int,
    ) : ListContentsUiEvent

    data class DeleteMovieClicked(
        val position: Int,
    ) : ListContentsUiEvent

    object BackClicked : ListContentsUiEvent

    object RetryClicked : ListContentsUiEvent
}
