package com.elhady.movies.ui.movieDetails


sealed interface MovieDetailsUiEvent {
    object ClickPlayTrailerEvent : MovieDetailsUiEvent
    data class ClickMovieEvent(val movieId: Int): MovieDetailsUiEvent
    object ClickBackButton: MovieDetailsUiEvent
    data class ClickCastEvent(val castId: Int): MovieDetailsUiEvent
    object ClickSeeReviewsEvent: MovieDetailsUiEvent
    data class MessageAppear(val status: String) : MovieDetailsUiEvent

    object ClickFavourite: MovieDetailsUiEvent

}