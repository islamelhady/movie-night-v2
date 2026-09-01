package com.elhady.movies.feature.details.presentation.peopledetails

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.ui.base.ErrorUiState

data class PeopleDetailsUiState(
    val peopleData: PersonInfoUiState = PersonInfoUiState(),
    val movies: List<PeopleMediaUiState> = emptyList(),
    val tvShows: List<PeopleMediaUiState> = emptyList(),
    val isPersonLoading: Boolean = true,
    val isMoviesLoading: Boolean = true,
    val isTvShowsLoading: Boolean = true,
    val error: ErrorUiState? = null,
) {

    val isLoading: Boolean
        get() = isPersonLoading || isMoviesLoading || isTvShowsLoading

    val isFailure: Boolean
        get() = error != null

    data class PeopleMediaUiState(
        val id: Int,
        val type: MediaType,
        val imageUrl: String,
        val rate: Double,
    )

    data class PersonInfoUiState(
        val id: Int = 0,
        val name: String = "",
        val imageUrl: String = "",
        val placeOfBirth: String = "",
        val gender: String = "",
        val acting: String = "",
        val numMovies: String = "",
        val biography: String = "",
    )
}