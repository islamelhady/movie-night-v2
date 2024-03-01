package com.elhady.viewmodel.home.homeUiState

import com.elhady.viewmodel.models.ActorUiState
import com.elhady.viewmodel.models.MediaUiState
import com.elhady.viewmodel.models.PopularUiState

data class HomeUiState(
    val popularMovieSlider: List<PopularUiState> = emptyList(),
    val upcomingMovie: List<MediaUiState> = emptyList(),
    val trendingMovie: List<MediaUiState> = emptyList(),
    val nowPlayingMovie: List<MediaUiState> = emptyList(),
    val topRatedMovie: List<MediaUiState> = emptyList(),
    val onTheAirSeries: List<MediaUiState> = emptyList(),
    val airingTodaySeries: List<MediaUiState> = emptyList(),
    val tvSeriesLists: List<MediaUiState> = emptyList(),
    val mysteryMovies: List<MediaUiState> = emptyList(),
    val adventureMovies: List<MediaUiState> = emptyList(),
    val popularPeople: List<ActorUiState> = emptyList(),
    val type: SeeAllType = SeeAllType.POPULAR_TV,
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList()
)