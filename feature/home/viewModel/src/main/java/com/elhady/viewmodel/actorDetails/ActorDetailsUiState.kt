package com.elhady.viewmodel.actorDetails


data class ActorDetailsUiState(
    val actorInfo: ActorInfoUiState = ActorInfoUiState(),
    val actorMovies: List<ActorMoviesUiState> = emptyList(),
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class ActorInfoUiState(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val gender: String = "",
    val birthday: String = "",
    val placeOfBirth: String = "",
    val biography: String = "",
    val knownForDepartment: String = "",
)

data class ActorMoviesUiState(
    val id: Int = 0,
    val image: String = "",
    val name: String = ""
)