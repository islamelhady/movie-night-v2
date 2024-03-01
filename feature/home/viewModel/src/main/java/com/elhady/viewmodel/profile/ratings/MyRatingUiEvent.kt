package com.elhady.viewmodel.profile.ratings

sealed interface MyRatingUiEvent {
    data class MovieEvent(val movieId: Int) : MyRatingUiEvent
    data class SeriesEvent(val tvShowId: Int) : MyRatingUiEvent
}