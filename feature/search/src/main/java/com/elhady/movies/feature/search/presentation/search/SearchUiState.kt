package com.elhady.movies.feature.search.presentation.search

import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.core.ui.state.PeopleUiState

data class SearchUiState(
    val searchQuery: String = "",
    val mediaType: SearchMedia = SearchMedia.MOVIE,
    val searchMediaResult: List<MovieHorizontalUiState> = emptyList(),
    val searchPeopleResult: List<PeopleUiState> = emptyList(),
    val genres: List<GenresUiState> = emptyList(),
    val selectedGenresId: Int? = null,
    val searchHistory: List<String> = emptyList(),
    val isSelectedPeople: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null
) {
    data class GenresUiState(
        val genreId: Int = 0,
        val genresName: String = "",
        val isSelected: Boolean = false
    )

    enum class SearchMedia {
        MOVIE,
        TV,
        PEOPLE
    }

    val isFailure: Boolean get() =
        error != null


    val isEmptyResult: Boolean get() =
        when(mediaType){
            SearchMedia.MOVIE, SearchMedia.TV -> searchMediaResult.isEmpty()
            SearchMedia.PEOPLE -> searchPeopleResult.isEmpty()
        }
}
