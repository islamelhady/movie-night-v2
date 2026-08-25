package com.elhady.movies.feature.watchlist.presentation.ratedmedia

import androidx.paging.PagingData
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class RatedMediaUiState (
    val rateType: RateType = RateType.Movies,
    val movies: Flow<PagingData<MovieHorizontalUiState>> = emptyFlow(),
    val error: ErrorUiState? = null,
    val isLoading: Boolean = false
){
    val failure = error != null
}
enum class RateType{
    Movies ,
    TvShows
}
