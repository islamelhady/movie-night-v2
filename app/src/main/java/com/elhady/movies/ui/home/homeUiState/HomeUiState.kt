package com.elhady.movies.ui.home.homeUiState

import com.elhady.movies.ui.home.HomeItem

data class HomeUiState(
    val popularMovie: HomeItem = HomeItem.Slider(emptyList()),
    val upcomingMovie: HomeItem = HomeItem.Upcoming(emptyList()),
    val trendingMovie: HomeItem = HomeItem.Trending(emptyList()),
    val nowPlayingMovie: HomeItem = HomeItem.NowPlaying(emptyList()),
    val topRatedMovie: HomeItem = HomeItem.TopRated(emptyList()),
    val onTheAirSeries: HomeItem = HomeItem.OnTheAirSeries(emptyList()),
    val airingTodaySeries: HomeItem = HomeItem.AiringTodaySeries(emptyList()),
    val tvSeriesLists: HomeItem = HomeItem.TVSeriesLists(emptyList()),
    val mysteryMovies: HomeItem = HomeItem.Mystery(emptyList()),
    val adventureMovies: HomeItem = HomeItem.Adventure(emptyList()),
    val isLoading: Boolean = false
)