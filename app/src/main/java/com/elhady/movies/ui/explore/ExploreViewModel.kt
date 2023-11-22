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
        Event(ExploreUiEvent.ClickTrendEvent(item))
    }

    fun scrollToTopScreen() {
        Event(ExploreUiEvent.ScrollToTopRecycler)
    }

    fun onClickMovies() {
        Event(ExploreUiEvent.ClickMoviesEvent)
    }

    fun onClickSeries() {
        Event(ExploreUiEvent.ClickSeriesEvent)
    }


    fun onClickActors() {
        Event(ExploreUiEvent.ClickActorsEvent)
    }

    fun onClickSearch() {
        Event(ExploreUiEvent.ClickSearchEvent)
    }
}