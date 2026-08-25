package com.elhady.movies.feature.watchlist.presentation.lists

data class ListMovieUiState(
    val id: Int? = null,
    val itemCount: Int? = null,
    val listType: String? = null,
    val name: String? = null,
    val posterPath: List<String>? = null,
)
