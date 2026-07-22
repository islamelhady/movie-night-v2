package com.elhady.movies.feature.details.presentation.moviedetails



data class FavoriteBodyUiState(
    val isFavorite: Boolean?,
    val mediaId: Int?,
    val mediaType: String,
)
