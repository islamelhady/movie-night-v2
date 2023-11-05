package com.elhady.movies.ui.explore

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetTrendingTvSeriesUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val trendingTvSeriesUseCase: GetTrendingTvSeriesUseCase,
    private val trendingUiStateMapper: TrendingUiStateMapper
) : BaseViewModel(), TrendingInteractionListener {

    private val _exploreUiState = MutableStateFlow(ExploreUiState())
    val exploreUiState = _exploreUiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        _exploreUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = trendingTvSeriesUseCase().map {
                trendingUiStateMapper.map(it)
            }

            _exploreUiState.update {
                it.copy(trendMediaResult = result, isLoading = false)
            }
        }
    }

    override fun onClickTrending() {
        TODO("Not yet implemented")
    }
}