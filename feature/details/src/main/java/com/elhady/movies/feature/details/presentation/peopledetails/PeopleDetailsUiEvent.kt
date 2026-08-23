package com.elhady.movies.feature.details.presentation.peopledetails

sealed interface PeopleDetailsUiEvent {

    object BackClicked : PeopleDetailsUiEvent

    object RetryClicked : PeopleDetailsUiEvent

    data class MovieClicked(
        val movieId: Int,
    ) : PeopleDetailsUiEvent

    data class TvShowClicked(
        val tvShowId: Int,
    ) : PeopleDetailsUiEvent
}
