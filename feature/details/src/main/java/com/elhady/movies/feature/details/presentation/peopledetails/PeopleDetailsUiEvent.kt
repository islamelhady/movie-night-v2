package com.elhady.movies.feature.details.presentation.peopledetails

sealed interface PeopleDetailsUiEvent {
    data class ClickMovieEvent(val itemId: Int) : PeopleDetailsUiEvent
    data class ClickTvShowsEvent(val itemId: Int) : PeopleDetailsUiEvent
    object BackNavigate : PeopleDetailsUiEvent

}
