package com.elhady.movies.presentation.ui.home

import com.elhady.movies.presentation.viewmodel.home.AiringTodayTvShowUiState
import com.elhady.movies.presentation.viewmodel.home.NowPlayingUiState
import com.elhady.movies.presentation.viewmodel.home.PopularMoviesUiState
import com.elhady.movies.presentation.viewmodel.home.PopularPeopleUiState
import com.elhady.movies.presentation.viewmodel.home.TopRatedUiState
import com.elhady.movies.presentation.viewmodel.home.TrendingMoviesUiState
import com.elhady.movies.presentation.viewmodel.home.TvShowUiState
import com.elhady.movies.presentation.viewmodel.home.UpComingMoviesUiState

sealed class HomeItem(val type: HomeItemType) {

    data class Slider(val list: List<UpComingMoviesUiState>) : HomeItem(HomeItemType.SLIDER)

    data class NowPlaying(val list: List<NowPlayingUiState>) : HomeItem(HomeItemType.NOW_PLAYING)

    data class TvShow(val list: List<TvShowUiState>) : HomeItem(HomeItemType.TV_SHOW)

    data class AiringTodayTvShow(val list: List<AiringTodayTvShowUiState>) : HomeItem(HomeItemType.AIRING_TODAY)

    data class Trending(val list: List<TrendingMoviesUiState>) : HomeItem(HomeItemType.TRENDING)

    data class TopRated(val list: List<TopRatedUiState>) : HomeItem(HomeItemType.TOP_RATED)

    data class PopularPeople(val list: List<PopularPeopleUiState>) : HomeItem(HomeItemType.POPULAR_PEOPLE)

    data class PopularMovies(val list: List<PopularMoviesUiState>) : HomeItem(HomeItemType.POPULAR_MOVIES)

}

enum class HomeItemType { SLIDER, NOW_PLAYING, TV_SHOW, AIRING_TODAY, TRENDING, TOP_RATED, POPULAR_PEOPLE, POPULAR_MOVIES }
