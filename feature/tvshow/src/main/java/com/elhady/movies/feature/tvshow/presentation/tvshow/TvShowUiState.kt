package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.math.roundToInt

data class TvShowUiState(
    val tvShowType: TvShowType = TvShowType.AIRING_TODAY,
    val tvShowAiringToday: Flow<PagingData<TvShowUi>> = emptyFlow(),
    val tvShowTopRated: Flow<PagingData<TvShowUi>> = emptyFlow(),
    val tvShowOnTheAir: Flow<PagingData<TvShowUi>> = emptyFlow(),
    val tvShowPopular: Flow<PagingData<TvShowUi>> = emptyFlow(),
    val errorList: List<String>? = emptyList(),
    val isLoading: Boolean = false
) {
    val isError: Boolean
        get() = errorList?.isNotEmpty() ?: false
}

data class TvShowUi(
    val tvId: Int?,
    val imageUrl: String?,
    val rate: Double?
) {
    fun formattedRate(): Double = if (rate == null) 0.0 else (rate * 10.0).roundToInt() / 10.0
}

enum class TvShowType {
    AIRING_TODAY,
    ON_THE_AIR,
    TOP_RATED,
    POPULAR
}
