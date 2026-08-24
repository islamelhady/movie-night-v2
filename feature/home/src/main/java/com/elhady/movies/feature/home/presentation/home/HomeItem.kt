package com.elhady.movies.feature.home.presentation.home

sealed interface HomeItem {

    data class Slider(
        val items: List<UpcomingMovieUiState>
    ) : HomeItem

    data class NowPlaying(
        val items: List<NowPlayingMovieUiState>
    ) : HomeItem

    data class TvShow(
        val items: List<TvShowUiState>
    ) : HomeItem

    data class AiringTodayTvShow(
        val items: List<AiringTodayTvShowUiState>
    ) : HomeItem

    data class TrendingMovie(
        val items: List<TrendingMovieUiState>
    ) : HomeItem

    data class TopRatedMovie(
        val items: List<TopRatedMovieUiState>
    ) : HomeItem

    data class PopularPeople(
        val items: List<PopularPeopleUiState>
    ) : HomeItem

    data class PopularMovies(
        val items: List<PopularMovieUiState>
    ) : HomeItem
}