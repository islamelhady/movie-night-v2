package com.elhady.viewmodel.home

import com.elhady.viewmodel.home.homeUiState.AdventureMoviesUiState
import com.elhady.viewmodel.home.homeUiState.AiringTodayTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.MysteryMoviesUiState
import com.elhady.viewmodel.home.homeUiState.NowPlayingMoviesUiState
import com.elhady.viewmodel.home.homeUiState.OnTheAirTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.PopularActorUiState
import com.elhady.viewmodel.home.homeUiState.PopularMoviesUiState
import com.elhady.viewmodel.home.homeUiState.TVShowsListsUiState
import com.elhady.viewmodel.home.homeUiState.TopRatedMoviesUiState
import com.elhady.viewmodel.home.homeUiState.TrendingMoviesUiState
import com.elhady.viewmodel.home.homeUiState.UpcomingMoviesUiState

sealed class HomeItem (val priority: Int){
    data class PopularMovieSlider(val items: List<PopularMoviesUiState>): HomeItem(0)
    data class Upcoming(val items: List<UpcomingMoviesUiState>, val type: ShowMoreType = ShowMoreType.UPCOMING_MOVIES): HomeItem(1)
    data class Trending(val items: List<TrendingMoviesUiState>, val type: ShowMoreType = ShowMoreType.TRENDING_MOVIES): HomeItem(2)
    data class TVSeriesLists(val items: List<TVShowsListsUiState>): HomeItem(3)
    data class NowPlaying(val items: List<NowPlayingMoviesUiState>, val type: ShowMoreType = ShowMoreType.NOW_PLAYING_MOVIES): HomeItem(4)
    data class OnTheAirSeries(val items: List<OnTheAirTVShowsUiState>, val type: ShowMoreType = ShowMoreType.ON_THE_AIR_TV_SHOW): HomeItem(5)
    data class TopRated(val items: List<TopRatedMoviesUiState>, val type: ShowMoreType = ShowMoreType.TOP_RATED_MOVIES): HomeItem(6)
    data class AiringTodaySeries(val items: List<AiringTodayTVShowsUiState>): HomeItem(7)
    data class Mystery(val items: List<MysteryMoviesUiState>, val type: ShowMoreType = ShowMoreType.MYSTERY_MOVIES): HomeItem(8)
    data class Adventure(val items: List<AdventureMoviesUiState>, val type: ShowMoreType = ShowMoreType.ADVENTURE_MOVIES): HomeItem(9)
    data class Actor(val items: List<PopularActorUiState>): HomeItem(10)
}