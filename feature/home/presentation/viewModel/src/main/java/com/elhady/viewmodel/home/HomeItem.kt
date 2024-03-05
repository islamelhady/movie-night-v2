package com.elhady.viewmodel.home

sealed class HomeItem (val priority: Int){
    data class PopularMovieSlider(val items: List<PopularUiState>): HomeItem(0)
    data class Upcoming(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.UPCOMING_MOVIE): HomeItem(1)
    data class Trending(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.TRENDING_MOVIE): HomeItem(2)
    data class TVSeriesLists(val items: List<MediaUiState>): HomeItem(3)
    data class NowPlaying(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.NOW_PLAYING_MOVIE): HomeItem(4)
    data class OnTheAirSeries(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.ON_THE_AIR_TV): HomeItem(5)
    data class TopRated(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.TOP_RATED_MOVIE): HomeItem(6)
    data class AiringTodaySeries(val items: List<MediaUiState>): HomeItem(7)
    data class Mystery(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.MYSTERY_MOVIE): HomeItem(8)
    data class Adventure(val items: List<MediaUiState>, val type: SeeAllType = SeeAllType.ADVENTURE_MOVIE): HomeItem(9)
    data class Actor(val items: List<ActorUiState>): HomeItem(10)
}