package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.ui.base.ErrorUiState

data class HomeUiState(
    val upcomingMovies: List<UpcomingMovieUiState> = emptyList(),
    val nowPlayingMovies: List<NowPlayingMovieUiState> = emptyList(),
    val trendingMovies: List<TrendingMovieUiState> = emptyList(),
    val popularPeople: List<PopularPersonUiState> = emptyList(),
    val popularMovies: List<PopularMovieUiState> = emptyList(),
    val topRatedMovies: List<TopRatedMovieUiState> = emptyList(),
    val tvShows: List<TvShowUiState> = emptyList(),
    val airingTodayTvShows: List<AiringTodayTvShowUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorUiState? = null,
) {
    val isError: Boolean
        get() = error != null
}

data class UpcomingMovieUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val genres: List<String>,
    val rate: Double,
)

data class NowPlayingMovieUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
)

data class TrendingMovieUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
)

data class PopularPersonUiState(
    val id: Int,
    val profilePath: String,
    val name: String,
)

data class PopularMovieUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
)

data class TopRatedMovieUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
)

data class TvShowUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
)

data class AiringTodayTvShowUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
)