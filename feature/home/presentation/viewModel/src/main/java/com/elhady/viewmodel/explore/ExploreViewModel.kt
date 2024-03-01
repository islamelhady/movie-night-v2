package com.elhady.viewmodel.explore

import com.elhady.base.BaseViewModel
import com.elhady.usecase.GetTrendingTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
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
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
       getTrending()
    }

    private fun getTrending(){
        tryToExecute(
            call = { trendingTvSeriesUseCase() },
            mapper = trendingUiStateMapper,
            onSuccess = ::onSuccessTrending,
            onError = ::onError
        )
    }

    private fun onSuccessTrending(list: List<TrendingMediaUiState>){
        _state.update { it.copy(trendMediaResult = list, isLoading = false, onErrors = emptyList()) }
    }

    private fun onError(th: Throwable){
        val listErrors = _state.value.onErrors.toMutableList()
        listErrors.add(th.message.toString())
        _state.update { it.copy(isLoading = false, onErrors = listErrors) }
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