package com.elhady.movies.ui.home

import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.PopularUiState
import com.elhady.movies.ui.models.MediaUiState

sealed class HomeItem (val priority: Int){
    data class Slider(val items: List<PopularUiState>): HomeItem(0)
    data class Upcoming(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.UPCOMING): HomeItem(1)
    data class Trending(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.TRENDING): HomeItem(2)
    data class TVSeriesLists(val items: List<MediaUiState>): HomeItem(3)
    data class NowPlaying(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.NOW_PLAYING): HomeItem(4)
    data class OnTheAirSeries(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.ON_THE_AIR_SERIES): HomeItem(5)
    data class TopRated(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.TOP_RATED): HomeItem(6)
    data class AiringTodaySeries(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.AIRING_TODAY_SERIES): HomeItem(7)
    data class Mystery(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.MYSTERY): HomeItem(8)
    data class Adventure(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.ADVENTURE): HomeItem(9)
    data class Actor(val items: List<ActorUiState>): HomeItem(10)
}