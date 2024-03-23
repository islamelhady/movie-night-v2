package com.elhady.movies.presentation.viewmodel.tv_shows

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.math.roundToInt

data class TVShowUIState(
    val tvShowsType: TVShowsType = TVShowsType.AIRING_TODAY,
    val tvShowAiringToday: Flow<PagingData<TVShowsUI>> = emptyFlow(),
    val tvShowTopRated: Flow<PagingData<TVShowsUI>> = emptyFlow(),
    val tvShowOnTheAir: Flow<PagingData<TVShowsUI>> = emptyFlow(),
    val tvShowPopular: Flow<PagingData<TVShowsUI>> = emptyFlow(),
    val errorList: List<String>? = emptyList(),
    val isLoading: Boolean = false
) {
    val isError: Boolean
        get() = errorList?.isNotEmpty() ?: false
}

data class TVShowsUI(
    val tvId: Int?,
    val imageUrl: String?,
    val rate: Double?
) {
    fun formattedRate(): Double = if (rate == null) 0.0 else (rate * 10.0).roundToInt() / 10.0
}

enum class TVShowsType {
    AIRING_TODAY,
    ON_THE_AIR,
    TOP_RATED,
    POPULAR
}

