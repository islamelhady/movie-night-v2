package com.elhady.movies.feature.watchlist.presentation.mylistdetails

sealed interface MyListDetailsUiEffect {

    data class NavigateToMovieDetails(
        val movieId: Int,
    ) : MyListDetailsUiEffect

    data class NavigateToTvShowDetails(
        val tvShowId: Int,
    ) : MyListDetailsUiEffect

    object NavigateBack : MyListDetailsUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : MyListDetailsUiEffect
}