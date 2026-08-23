package com.elhady.movies.feature.details.presentation.peopledetails

sealed interface PeopleDetailsUiEffect {

    object NavigateBack : PeopleDetailsUiEffect

    data class NavigateToMovieDetails(
        val movieId: Int,
    ) : PeopleDetailsUiEffect

    data class NavigateToTvDetails(
        val tvShowId: Int,
    ) : PeopleDetailsUiEffect

    data class ShowSnackBar(
        val message: String,
    ) : PeopleDetailsUiEffect
}
