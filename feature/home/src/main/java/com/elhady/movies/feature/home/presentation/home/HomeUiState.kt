package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.ui.base.ErrorUiState
import kotlin.math.roundToInt

data class HomeUiState(
    val upcomingMovies: List<UpcomingMovieUiState> = emptyList(),
    val nowPlayingMovies: List<NowPlayingMovieUiState> = emptyList(),
    val trendingMovies: List<TrendingMovieUiState> = emptyList(),
    val popularPeople: List<PopularPeopleUiState> = emptyList(),
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
){
    fun formattedRate(): Double = (rate * 10).roundToInt() / 10.0
}

data class NowPlayingMovieUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
)

data class TrendingMovieUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
){
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class PopularPeopleUiState(
    val id: Int,
    val profilePath: String,
    val name: String,
)

data class PopularMovieUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
){
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class TopRatedMovieUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
){
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class TvShowUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class AiringTodayTvShowUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double,
){
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}