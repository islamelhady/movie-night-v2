package com.elhady.movies.ui.movieDetails

data class DetailsUiState(
    val movieDetailsResult: DetailsItem = DetailsItem.Header(MovieDetailsUiState()),
    val movieCastResult: DetailsItem = DetailsItem.Cast(emptyList()),
    val similarMoviesResult: DetailsItem = DetailsItem.Similar(emptyList()),
    val isLoading: Boolean = false,
    val errorUIStates: List<Error> = emptyList(),
)

data class ErrorUiState(
    val message: String = "",
    val code: Int = 0,
)