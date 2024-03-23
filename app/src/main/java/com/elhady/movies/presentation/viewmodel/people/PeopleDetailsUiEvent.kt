package com.elhady.movies.presentation.viewmodel.people

sealed interface PeopleDetailsUiEvent {
    data class ClickMovieEvent(val itemId: Int) : PeopleDetailsUiEvent
    data class ClickTvShowsEvent(val itemId: Int) : PeopleDetailsUiEvent
    object BackNavigate : PeopleDetailsUiEvent

}