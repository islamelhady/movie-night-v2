package com.elhady.movies.ui.home

import com.elhady.movies.domain.enums.HomeItemType
import com.elhady.movies.ui.models.PopularUiState
import com.elhady.movies.ui.models.MediaUiState

sealed interface HomeItem{
    data class Slider(val items: List<PopularUiState>): HomeItem
    data class Upcoming(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.UPCOMING): HomeItem
    data class Trending(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.TRENDING): HomeItem
    data class NowPlaying(val items: List<MediaUiState>, val type: HomeItemType = HomeItemType.NOW_PLAYING): HomeItem
}