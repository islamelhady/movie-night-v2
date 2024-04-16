package com.elhady.movies.presentation.viewmodel.moviedetails



data class FavoriteBodyUiState(
    val isFavorite: Boolean?,
    val mediaId: Int?,
    val mediaType: String,
)