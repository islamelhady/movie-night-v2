package com.elhady.movies.feature.showmore.presentation.showmore

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.UiText
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
    val errors: ErrorUiState? = null,
    private val stringsRes: StringsRes
) {

    val title: UiText = when (showMoreType) {
        ShowMoreType.POPULAR_MOVIES -> stringsRes.popularMovies
        ShowMoreType.TOP_RATED_MOVIES -> stringsRes.topRatedMovies
        ShowMoreType.TRENDING_MOVIES -> stringsRes.trendingMovies
        ShowMoreType.AIRING_TODAY_TV -> stringsRes.airingTodayTvShow
        ShowMoreType.TOP_RATED_TV -> stringsRes.topRatedTvShow
        ShowMoreType.POPULAR_TV -> stringsRes.popularTvShow
        ShowMoreType.ON_THE_AIR_TV -> stringsRes.onTheAirTvShow
    }

}

data class ShowMoreUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val year: String,
    val genreEntities: String,
    val rate: Double,
    val type: ListType = ListType.MOVIE
) {
    fun formattedRate(): Double = (rate * 10.0).roundToInt() / 10.0
}
