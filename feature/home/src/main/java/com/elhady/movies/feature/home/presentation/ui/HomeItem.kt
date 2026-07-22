package com.elhady.movies.feature.home.presentation.ui

import com.elhady.movies.feature.home.presentation.AiringTodayTvShowUiState
import com.elhady.movies.feature.home.presentation.NowPlayingUiState
import com.elhady.movies.feature.home.presentation.PopularMoviesUiState
import com.elhady.movies.feature.home.presentation.PopularPeopleUiState
import com.elhady.movies.feature.home.presentation.TopRatedUiState
import com.elhady.movies.feature.home.presentation.TrendingMoviesUiState
import com.elhady.movies.feature.home.presentation.TvShowUiState
import com.elhady.movies.feature.home.presentation.UpComingMoviesUiState

sealed class HomeItem(val type: HomeItemType) {

    data class Slider(val list: List<UpComingMoviesUiState>) : HomeItem(HomeItemType.SLIDER)
    data class NowPlaying(val list: List<NowPlayingUiState>) : HomeItem(HomeItemType.NOW_PLAYING)
    data class TvShow(val list: List<TvShowUiState>) : HomeItem(HomeItemType.TV_SHOW)
    data class AiringTodayTvShow(val list: List<AiringTodayTvShowUiState>) : HomeItem(HomeItemType.AIRING_TODAY)
    data class Trending(val list: List<TrendingMoviesUiState>) : HomeItem(HomeItemType.TRENDING)
    data class TopRated(val list: List<TopRatedUiState>) : HomeItem(HomeItemType.TOP_RATED)
    data class PopularPeople(val list: List<PopularPeopleUiState>) : HomeItem(HomeItemType.POPULAR_PEOPLE)
    data class PopularMovies(val list: List<PopularMoviesUiState>) :
        HomeItem(HomeItemType.POPULAR_MOVIES)
}

enum class HomeItemType { SLIDER, NOW_PLAYING, TV_SHOW, AIRING_TODAY, TRENDING, TOP_RATED, POPULAR_PEOPLE, POPULAR_MOVIES }
