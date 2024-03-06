package com.elhady.ui.home

import com.elhady.viewmodel.home.ShowMoreType
import com.elhady.viewmodel.home.homeUiState.AiringTodayTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.MoviesUiState
import com.elhady.viewmodel.home.homeUiState.NowPlayingMoviesUiState
import com.elhady.viewmodel.home.homeUiState.OnTheAirTVShowsUiState
import com.elhady.viewmodel.home.homeUiState.PopularActorUiState
import com.elhady.viewmodel.home.homeUiState.PopularMoviesUiState
import com.elhady.viewmodel.home.homeUiState.TVShowsListsUiState

sealed class HomeItem (val priority: Int){
    data class PopularMovieSlider(val items: List<PopularMoviesUiState>): HomeItem(0)
    data class UpcomingMovies(val items: List<MoviesUiState>, val type: ShowMoreType = ShowMoreType.UPCOMING_MOVIES): HomeItem(1)
    data class TrendingMovies(val items: List<MoviesUiState>, val type: ShowMoreType = ShowMoreType.TRENDING_MOVIES): HomeItem(2)
    data class TvShowsLists(val items: List<TVShowsListsUiState>): HomeItem(3)
    data class NowPlayingMovies(val items: List<NowPlayingMoviesUiState>, val type: ShowMoreType = ShowMoreType.NOW_PLAYING_MOVIES): HomeItem(4)
    data class OnTheAirTvShows(val items: List<OnTheAirTVShowsUiState>, val type: ShowMoreType = ShowMoreType.ON_THE_AIR_TV_SHOW): HomeItem(5)
    data class TopRatedMovies(val items: List<MoviesUiState>, val type: ShowMoreType = ShowMoreType.TOP_RATED_MOVIES): HomeItem(6)
    data class AiringTodayTvShows(val items: List<AiringTodayTVShowsUiState>): HomeItem(7)
    data class MysteryMovies(val items: List<MoviesUiState>, val type: ShowMoreType = ShowMoreType.MYSTERY_MOVIES): HomeItem(8)
    data class AdventureMovies(val items: List<MoviesUiState>, val type: ShowMoreType = ShowMoreType.ADVENTURE_MOVIES): HomeItem(9)
    data class PopularActor(val items: List<PopularActorUiState>): HomeItem(10)
}