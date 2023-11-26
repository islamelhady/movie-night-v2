package com.elhady.movies.ui.explore

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetTrendingTvSeriesUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val trendingTvSeriesUseCase: GetTrendingTvSeriesUseCase,
    private val trendingUiStateMapper: TrendingUiStateMapper
) : BaseViewModel<ExploreUiState, ExploreUiEvent>(ExploreUiState()), TrendingInteractionListener {

    init {
        getData()
    }

    override fun getData() {
        viewModelScope.launch {
            val result = trendingTvSeriesUseCase().map {
                trendingUiStateMapper.map(it)
            }

            _state.update {
                it.copy(trendMediaResult = result)
            }
        }
    }

    override fun onClickTrending(item: TrendingMediaUiState) {
        sendEvent(ExploreUiEvent.ClickTrendEvent(item))
    }

    fun scrollToTopScreen() {
        sendEvent(ExploreUiEvent.ScrollToTopRecycler)
    }

    fun onClickMovies() {
        sendEvent(ExploreUiEvent.ClickMoviesEvent)
    }

    fun onClickSeries() {
        sendEvent(ExploreUiEvent.ClickSeriesEvent)
    }


    fun onClickActors() {
        sendEvent(ExploreUiEvent.ClickActorsEvent)
    }

    fun onClickSearch() {
        sendEvent(ExploreUiEvent.ClickSearchEvent)
    }
}