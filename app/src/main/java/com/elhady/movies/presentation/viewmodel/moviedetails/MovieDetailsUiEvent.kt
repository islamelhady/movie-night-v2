package com.elhady.movies.presentation.viewmodel.moviedetails


sealed interface MovieDetailsUiEvent{

    data class ApplyRatingEvent(val message: String) : MovieDetailsUiEvent
    data class NavigateToPeopleDetailsEvent(val itemId: Int) : MovieDetailsUiEvent
    data class NavigateToMovieDetailsEvent(val movieId: Int) : MovieDetailsUiEvent
    data class ShowSnackBarMessageEvent(val message: String): MovieDetailsUiEvent
    object RateMovieEvent : MovieDetailsUiEvent
    object SaveToListEvent : MovieDetailsUiEvent
    data class PlayVideoTrailerEvent(val videoKey: String) : MovieDetailsUiEvent
    data class NavigateToShowMoreEvent(val movieId: Int) : MovieDetailsUiEvent
    object OnClickBackEvent : MovieDetailsUiEvent

    // region save to list event
    object CloseEvent: MovieDetailsUiEvent
    object AddListEvent: MovieDetailsUiEvent
    object DoneEvent: MovieDetailsUiEvent
    object CreateListEvent: MovieDetailsUiEvent
}
