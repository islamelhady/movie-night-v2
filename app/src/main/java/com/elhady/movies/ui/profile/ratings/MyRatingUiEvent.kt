package com.elhady.movies.ui.profile.ratings

sealed interface MyRatingUiEvent {
    data class MovieEvent(val movieId: Int) : MyRatingUiEvent
    data class SeriesEvent(val tvShowId: Int) : MyRatingUiEvent
}