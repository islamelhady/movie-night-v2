package com.elhady.movies.ui.movieDetails

data class DetailsUiState(
    val movieDetailsResult: DetailsItem = DetailsItem.Header(MovieDetailsUiState()),
    val movieCastResult: DetailsItem = DetailsItem.Cast(emptyList())
)
