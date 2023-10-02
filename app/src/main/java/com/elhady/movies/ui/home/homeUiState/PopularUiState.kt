package com.elhady.movies.ui.home.homeUiState

data class PopularUiState(
    val movieId: Int,
    val imageUrl: String,
    val title: String,
    val movieRate: Double,
    val genre: List<String>
)