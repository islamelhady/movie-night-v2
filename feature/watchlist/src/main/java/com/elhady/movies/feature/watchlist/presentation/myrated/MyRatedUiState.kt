package com.elhady.movies.feature.watchlist.presentation.myrated

import androidx.paging.PagingData
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MyRatedUiState (
    val myRateType: RateType = RateType.Movies,
    val myRatedMedia: Flow<PagingData<MovieHorizontalUiState>> = emptyFlow(),
    val errorList: List<String>? = emptyList(),
    val isLoading: Boolean = false
)
enum class RateType{
    Movies ,
    TVShows
}
