package com.elhady.movies.feature.details.presentation.moviedetails

sealed interface MovieDetailsUiEvent {
    object BackClicked : MovieDetailsUiEvent
    object PlayClicked : MovieDetailsUiEvent
    object RateClicked : MovieDetailsUiEvent
    object SaveClicked : MovieDetailsUiEvent
    data class ShowMoreClicked(val movieId: Int) : MovieDetailsUiEvent
    data class MovieClicked(val movieId: Int) : MovieDetailsUiEvent
    data class PersonClicked(val personId: Int) : MovieDetailsUiEvent
    data class RatingChanged(val rating: Float) : MovieDetailsUiEvent
    object RatingSubmitted : MovieDetailsUiEvent
    data class RetryClicked(val movieId: Int) : MovieDetailsUiEvent

    // Save to List Bottom Sheet interactions
    data class ChipClicked(val id: Int) : MovieDetailsUiEvent
    object DoneClicked : MovieDetailsUiEvent
    object FavouriteClicked : MovieDetailsUiEvent
    object WatchlistClicked : MovieDetailsUiEvent
    data class CreateListClicked(val name: String) : MovieDetailsUiEvent
    object CloseClicked : MovieDetailsUiEvent
    object AddListClicked : MovieDetailsUiEvent
    object DismissPlayerClicked : MovieDetailsUiEvent
}
