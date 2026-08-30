package com.elhady.movies.feature.details.presentation.moviedetails



import com.elhady.movies.core.common.MediaType

data class FavoriteBodyUiState(
    val isFavorite: Boolean?,
    val mediaId: Int?,
    val mediaType: MediaType,
)
