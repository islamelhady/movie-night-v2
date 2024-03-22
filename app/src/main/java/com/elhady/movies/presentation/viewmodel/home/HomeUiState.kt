package com.elhady.movies.presentation.viewmodel.home

import kotlin.math.roundToInt

data class HomeUiState(
    val upComingMovies: List<UpComingMoviesUiState> = emptyList(),
    val nowPlayingMovies: List<NowPlayingUiState> = emptyList(),
    val trendingMovies: List<TrendingMoviesUiState> = emptyList(),
    val popularPeople: List<PopularPeopleUiState> = emptyList(),
    val popularMovies: List<PopularMoviesUiState> = emptyList(),
    val topRated: List<TopRatedUiState> = emptyList(),
    val tvShow: List<TvShowUiState> = emptyList(),
    val airingTodayTvShow: List<AiringTodayTvShowUiState> = emptyList(),
    val onErrors: List<String> = emptyList(),
    val isLoading: Boolean = false,
){
    val isError: Boolean
        get() = onErrors.isNotEmpty()
}

data class UpComingMoviesUiState(
    val id: Int = 0,
    val imageUrl: String = "",
    val title: String = "",
    val genres: List<String> = emptyList(),
    val rate: Double = 0.0
){
    fun formattedRate(): Double = (rate * 10).roundToInt() / 10.0
}

data class NowPlayingUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
)


data class TrendingMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class PopularPeopleUiState(
    val id: Int,
    val profilePath: String,
    val name: String
)

data class PopularMoviesUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}


data class TopRatedUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class TvShowUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}

data class AiringTodayTvShowUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 100).roundToInt() / 100.0
}