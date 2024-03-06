package com.elhady.viewmodel.showMore

import androidx.paging.PagingData
import com.elhady.base.StringsRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.math.roundToInt

data class ShowMoreUiState(
    val showMoreTrendingMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreNowPlayingMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreTopRatedMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreOnTheAirMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreTopRatedTvShows: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreLatestTvShows: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMorePopularTvShows: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreMysteryMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreAdventureMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreUpcomingMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMorePopularActorMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreType: ShowMoreType = ShowMoreType.LATEST_TV_SHOW,
    val isLoading: Boolean = false,
    val onErrors: List<String> = emptyList(),
    private val stringsRes: StringsRes
){
    val isError: Boolean
        get() = onErrors.isNotEmpty()

    val title: String = when(showMoreType){
        ShowMoreType.TOP_RATED_TV_SHOW -> stringsRes.popularMovies
        ShowMoreType.POPULAR_TV_SHOW -> stringsRes.popularTvShow
        ShowMoreType.LATEST_TV_SHOW -> stringsRes.latestTvShow
        ShowMoreType.ON_THE_AIR_TV_SHOW -> stringsRes.onTheAirTvShow
        ShowMoreType.UPCOMING_MOVIES -> stringsRes.upcomingMovies
        ShowMoreType.TRENDING_MOVIES -> stringsRes.trendingMovies
        ShowMoreType.NOW_PLAYING_MOVIES -> stringsRes.nowPlayingMovies
        ShowMoreType.TOP_RATED_MOVIES -> stringsRes.topRatedMovies
        ShowMoreType.MYSTERY_MOVIES -> stringsRes.mysteryMovies
        ShowMoreType.ADVENTURE_MOVIES -> stringsRes.adventureMovies
        ShowMoreType.POPULAR_ACTORS -> stringsRes.popularActors
    }
}

data class ShowMoreUi(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    val year: String = "",
    val genreEntities: String = "",
    val rate: Double = 0.0,
){
    fun formattedRate(): Double = (rate * 10.0).roundToInt() / 10.0
}