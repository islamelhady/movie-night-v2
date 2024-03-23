package com.elhady.movies.presentation.viewmodel.mylistdetails


sealed interface MyListDetailsUiEvent {
    data class NavigateToMovieDetails(val movieId: Int) : MyListDetailsUiEvent
    data class NavigateToTvDetails(val movieId: Int) : MyListDetailsUiEvent

    data class ShowSnackBar(val message:String): MyListDetailsUiEvent

    object OnClickBack : MyListDetailsUiEvent
}