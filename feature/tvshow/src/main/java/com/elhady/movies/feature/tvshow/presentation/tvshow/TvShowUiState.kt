package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.paging.PagingData
import com.elhady.movies.core.ui.base.ErrorUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.math.roundToInt

data class TvShowUiState(
    val tvShowType: TvShowType = TvShowType.AIRING_TODAY,
    val tvShowAiringToday: Flow<PagingData<ShowUiState>> = emptyFlow(),
    val tvShowTopRated: Flow<PagingData<ShowUiState>> = emptyFlow(),
    val tvShowOnTheAir: Flow<PagingData<ShowUiState>> = emptyFlow(),
    val tvShowPopular: Flow<PagingData<ShowUiState>> = emptyFlow(),
    val error: ErrorUiState? = null,
    val isLoading: Boolean = false
)

data class ShowUiState(
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
