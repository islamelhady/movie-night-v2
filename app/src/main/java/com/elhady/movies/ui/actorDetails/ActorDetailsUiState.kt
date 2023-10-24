package com.elhady.movies.ui.actorDetails


data class ActorDetailsUiState(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val gender: String = "",
    val birthday: String = "",
    val placeOfBirth: String = "",
    val biography: String = "",
    val knownForDepartment: String = "",
    val actorMovies: List<ActorMoviesUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: List<Error> = emptyList()
)

data class Error(
    val message: String = ""
)