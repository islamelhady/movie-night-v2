package com.elhady.viewmodel.showMore

import androidx.paging.PagingData
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
){
    val isError: Boolean
        get() = onErrors.isNotEmpty()

    val title: String = when(showMoreType){
        ShowMoreType.TOP_RATED_TV_SHOW ->  ShowMoreType.TOP_RATED_TV_SHOW.value
        ShowMoreType.POPULAR_TV_SHOW -> ShowMoreType.POPULAR_TV_SHOW.value
        ShowMoreType.LATEST_TV_SHOW -> ShowMoreType.LATEST_TV_SHOW.value
        ShowMoreType.ON_THE_AIR_TV_SHOW -> ShowMoreType.ON_THE_AIR_TV_SHOW.value
        ShowMoreType.UPCOMING_MOVIES -> ShowMoreType.UPCOMING_MOVIES.value
        ShowMoreType.TRENDING_MOVIES -> ShowMoreType.TRENDING_MOVIES.value
        ShowMoreType.NOW_PLAYING_MOVIES -> ShowMoreType.NOW_PLAYING_MOVIES.value
        ShowMoreType.TOP_RATED_MOVIES -> ShowMoreType.TOP_RATED_MOVIES.value
        ShowMoreType.MYSTERY_MOVIES -> ShowMoreType.MYSTERY_MOVIES.value
        ShowMoreType.ADVENTURE_MOVIES -> ShowMoreType.ADVENTURE_MOVIES.value
        ShowMoreType.POPULAR_ACTORS -> ShowMoreType.POPULAR_ACTORS.value
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