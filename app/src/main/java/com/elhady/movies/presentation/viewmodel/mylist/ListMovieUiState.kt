package com.elhady.movies.presentation.viewmodel.mylist

data class ListMovieUiState(
    val id: Int? = null,
    val itemCount: Int? = null,
    val listType: String? = null,
    val name: String? = null,
    val posterPath: List<String>? = null,
)