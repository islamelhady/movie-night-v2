package com.elhady.movies.ui.home

data class PopularUiState(
    val movieId: Int? = 0,
    val imageUrl: String? = "",
    val title: String? ="",
    val movieRate: Double? = 0.0,
    val genre: List<String>

)