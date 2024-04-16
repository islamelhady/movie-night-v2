package com.elhady.movies.presentation.viewmodel.explore

import kotlin.math.roundToInt

data class ExploreUiState(
    val trendingMoviesToday: List<TrendingMoviesUiState> = emptyList(),
    val isLoading: Boolean = false,
    val layoutManager: Boolean = false,
    val onErrors: List<String> = emptyList(),
){

    data class TrendingMoviesUiState(
        val id: Int,
        val imageUrl: String,
        val rate: Double,
        val title: String,
        val year: String,
        val genres: String
    ) {
        fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
    }

    val isFailure: Boolean get() = onErrors.isNotEmpty()
    val isEmptyResult: Boolean get() = trendingMoviesToday.isEmpty()

}
