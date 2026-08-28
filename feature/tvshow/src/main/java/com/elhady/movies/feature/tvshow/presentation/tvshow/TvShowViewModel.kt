package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetOnTheAirTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetPopularTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTopRatedTvShowsUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.tvshow.presentation.tvshow.mapper.TvShowUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase,
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val tvShowUiMapper: TvShowUiMapper,
    private val stringsRes: StringsRes
) : BaseViewModel<TvShowUiState, TvShowUiEffect>(TvShowUiState()) {

    private var pagingJob: Job? = null

    init {
        getAiringTodayTvShows()
    }

    fun onEvent(event: TvShowUiEvent) {
        when (event) {
            TvShowUiEvent.OnTheAirTvShowClicked -> getOnTheAirTvShows()
            TvShowUiEvent.AiringTodayTvShowClicked -> getAiringTodayTvShows()
            TvShowUiEvent.TopRatedTvShowClicked -> getTopRatedTvShows()
            TvShowUiEvent.PopularTvShowClicked -> getPopularTvShows()
            TvShowUiEvent.RetryClicked -> getData()
            TvShowUiEvent.ToTopClicked -> sendEffect(TvShowUiEffect.ScrollToTop)
            is TvShowUiEvent.TvShowItemClicked -> sendEffect(TvShowUiEffect.NavigateToTvShowDetails(event.tvId))
        }
    }

    private fun getData() {
        when (_state.value.tvShowType) {
            TvShowType.AIRING_TODAY -> getAiringTodayTvShows()
            TvShowType.ON_THE_AIR -> getOnTheAirTvShows()
            TvShowType.TOP_RATED -> getTopRatedTvShows()
            TvShowType.POPULAR -> getPopularTvShows()
        }
    }

    private fun getAiringTodayTvShows() {
        _state.update { it.copy(tvShowType = TvShowType.AIRING_TODAY, isLoading = true, error = null) }
        pagingJob?.cancel()
        pagingJob = wrapperPager(
            data = { getAiringTodayTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessAiringTodayTvShows,
            onError = { onError(it.toErrorUiState()) }
        )
    }

    private fun onSuccessAiringTodayTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowAiringToday = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    private fun getOnTheAirTvShows() {
        _state.update { it.copy(tvShowType = TvShowType.ON_THE_AIR, isLoading = true, error = null) }
        pagingJob?.cancel()
        pagingJob = wrapperPager(
            data = { getOnTheAirTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessOnTheAirTvShows,
            onError = { onError(it.toErrorUiState()) }
        )
    }

    private fun onSuccessOnTheAirTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowOnTheAir = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    private fun getPopularTvShows() {
        _state.update { it.copy(tvShowType = TvShowType.POPULAR, isLoading = true, error = null) }
        pagingJob?.cancel()
        pagingJob = wrapperPager(
            data = { getPopularTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessPopularTvShows,
            onError = { onError(it.toErrorUiState()) }
        )
    }

    private fun onSuccessPopularTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowPopular = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    private fun getTopRatedTvShows() {
        _state.update { it.copy(tvShowType = TvShowType.TOP_RATED, isLoading = true, error = null) }
        pagingJob?.cancel()
        pagingJob = wrapperPager(
            data = { getTopRatedTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessTopRatedTvShows,
            onError = { onError(it.toErrorUiState()) }
        )
    }

    private fun onSuccessTopRatedTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowTopRated = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    private fun onError(errorUiState: ErrorUiState) {
        _state.update {
            it.copy(
                error = errorUiState,
                isLoading = false
            )
        }
    }

    fun onPagingLoadStateChanged(loadStates: CombinedLoadStates) {
        val errorState = loadStates.source.refresh as? LoadState.Error
            ?: loadStates.source.append as? LoadState.Error
            ?: loadStates.source.prepend as? LoadState.Error

        errorState?.let {
            if (it.error is AppException.NoNetwork) {
                sendEffect(TvShowUiEffect.ShowSnackBar(stringsRes.noNetworkConnection))
            }
        }

        when (val refreshState = loadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, error = null)
                }
            }
            LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, error = null)
                }
            }
            is LoadState.Error -> {
                val error = refreshState.error
                if (error is AppException) {
                    onError(error.toErrorUiState())
                }
            }
        }
    }
}
