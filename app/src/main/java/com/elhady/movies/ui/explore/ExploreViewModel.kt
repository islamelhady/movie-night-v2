package com.elhady.movies.ui.explore

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetTrendingTvSeriesUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.utilities.Event
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

    private val _exploreUiEvent = MutableStateFlow<Event<ExploreUiEvent>?>(null)
    val exploreUiEvent = _exploreUiEvent.asStateFlow()
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

    override fun onClickTrending(item: TrendingMediaUiState) {
        _exploreUiEvent.update {
            Event(ExploreUiEvent.ClickTrendEvent(item))
        }
    }

    fun scrollToTopScreen() {
        _exploreUiEvent.update {
            Event(ExploreUiEvent.ScrollToTopRecycler)
        }
    }

    fun onClickActors(){
        _exploreUiEvent.update {
            Event(ExploreUiEvent.ClickActorsEvent)
        }
    }
}