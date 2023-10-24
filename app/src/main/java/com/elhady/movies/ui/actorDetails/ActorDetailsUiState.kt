package com.elhady.movies.ui.actorDetails

import com.elhady.movies.ui.actors.ActorsUiState

data class ActorDetailsUiState(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val gender: String = "",
    val birthday: String = "",
    val placeOfBirth: String = "",
    val biography: String = "",
    val knownForDepartment: String = "",
    val actorMovies: List<ActorMoviesUiState> = emptyList()
)