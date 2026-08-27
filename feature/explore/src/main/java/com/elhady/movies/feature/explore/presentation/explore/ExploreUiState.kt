package com.elhady.movies.feature.explore.presentation.explore

import com.elhady.movies.core.ui.base.ErrorUiState
import kotlin.math.roundToInt

data class ExploreUiState(
    val trendingMoviesToday: List<TrendingMoviesUiState> = emptyList(),
    val exploreItems: List<ExploreItem> = emptyList(),
    val isLoading: Boolean = false,
    val isGridLayout: Boolean = false,
    val errors: ErrorUiState? = null,
) {

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
}
