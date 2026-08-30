package com.elhady.movies.feature.watchlist.presentation.lists

import com.elhady.movies.core.common.MediaType

data class ListMovieUiState(
    val id: Int? = null,
    val itemCount: Int? = null,
    val listType: MediaType = MediaType.MOVIE,
    val name: String? = null,
    val posterPath: List<String> = emptyList(),
)
