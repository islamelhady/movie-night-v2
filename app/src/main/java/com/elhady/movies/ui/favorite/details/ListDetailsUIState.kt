package com.elhady.movies.ui.favorite.details

import com.elhady.movies.domain.enums.MediaType

data class ListDetailsUIState(
    val savedMedia: List<FavMediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val onErrors: List<String> = emptyList()
)

data class FavMediaUiState(
    val mediaID: Int = 0,
    val title: String = "",
    val mediaType: String = MediaType.MOVIES.value,
    val voteAverage: Double = 0.0,
    val releaseDate: String = "",
    val image: String = "",
)