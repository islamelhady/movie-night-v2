package com.elhady.movies.feature.search.presentation.search

sealed interface SearchUiEvent {
    data class QueryChanged(val query: String) : SearchUiEvent
    object FilterClicked : SearchUiEvent
    data class GenreClicked(val genreId: Int) : SearchUiEvent
    object ApplyFilterClicked : SearchUiEvent
    object ClearClicked : SearchUiEvent
    object MediaTypeMovieClicked : SearchUiEvent
    object MediaTypeTvClicked : SearchUiEvent
    object MediaTypePeopleClicked : SearchUiEvent
    object BackClicked : SearchUiEvent
    object TryAgainClicked : SearchUiEvent
    data class MovieClicked(val movieId: Int) : SearchUiEvent
    data class TvClicked(val tvId: Int) : SearchUiEvent
    data class PeopleClicked(val peopleId: Int) : SearchUiEvent
}
