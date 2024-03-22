package com.elhady.movies.presentation.viewmodel.showmore

import androidx.paging.PagingData
import com.elhady.movies.core.bases.ListType
import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.presentation.viewmodel.tv_shows.TVShowsUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.math.roundToInt

data class ShowMoreUiState(
    val showMoreTopRatedMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMorePopularMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreTrendingMovies: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreAiringTodayTvShow: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreTopRatedTvShow: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMoreOnTheAirTvShow: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val showMorePopularTvShow: Flow<PagingData<ShowMoreUi>> = emptyFlow(),
    val isLoading: Boolean = false,
    val showMoreType: ShowMoreType = ShowMoreType.POPULAR_MOVIES,
    val errorList: List<String>? = null,
    private val stringsRes: StringsRes
) {

    val title: String = when (showMoreType) {
        ShowMoreType.POPULAR_MOVIES -> stringsRes.popularMovies
        ShowMoreType.TOP_RATED_MOVIES -> stringsRes.topRatedMovies
        ShowMoreType.TRENDING_MOVIES -> stringsRes.trendingMovies
        ShowMoreType.AIRING_TODAY_TV -> stringsRes.airingTodayTvShow
        ShowMoreType.TOP_RATED_TV -> stringsRes.topRatedTvShow
        ShowMoreType.POPULAR_TV -> stringsRes.popularTvShow
        ShowMoreType.ON_THE_AIR_TV -> stringsRes.onTheAirTvShow
    }

    val isError: Boolean = errorList?.isNotEmpty() ?: false
}

data class ShowMoreUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val year: String,
    val genreEntities: String,
    val rate: Double,
    val type: ListType = ListType.movie
) {
    fun formattedRate(): Double = (rate * 10.0).roundToInt() / 10.0
}

enum class ShowMoreType {
    POPULAR_MOVIES,
    TOP_RATED_MOVIES,
    TRENDING_MOVIES,
    AIRING_TODAY_TV,
    TOP_RATED_TV,
    POPULAR_TV,
    ON_THE_AIR_TV
}
