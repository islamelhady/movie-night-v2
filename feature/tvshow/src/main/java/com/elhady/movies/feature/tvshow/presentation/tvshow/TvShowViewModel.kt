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
import com.elhady.movies.feature.tvshow.presentation.tvshow.mapper.TvShowUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase,
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val tvShowUiMapper: TvShowUiMapper
) : BaseViewModel<TvShowUiState, TvShowUiEffect>(TvShowUiState()) {


    init {
        onEvent(TvShowUiEvent.AiringTodayTvShowClicked)
    }

    fun onEvent(event: TvShowUiEvent) {
        when (event) {
            TvShowUiEvent.AiringTodayTvShowClicked -> {
                getAiringTodayTvShows()
            }

            TvShowUiEvent.OnTheAirTvShowClicked -> {
                getOnTheAirTvShows()
            }

            TvShowUiEvent.PopularTvShowClicked -> {
                getPopularTvShows()
            }

            TvShowUiEvent.TopRatedTvShowClicked -> {
                getTopRatedTvShows()
            }

            TvShowUiEvent.RetryClicked -> {
                getData()
            }

            TvShowUiEvent.ToTopClicked -> {
                sendEffect(TvShowUiEffect.ScrollToTop)
            }

            is TvShowUiEvent.TvShowItemClicked -> {
                sendEffect(TvShowUiEffect.NavigateToTvShowDetails(event.tvId))
            }
        }
    }

    ///region get data
    private fun getData() {
        when (_state.value.tvShowType) {
            TvShowType.AIRING_TODAY -> getAiringTodayTvShows()
            TvShowType.ON_THE_AIR -> getOnTheAirTvShows()
            TvShowType.TOP_RATED -> getTopRatedTvShows()
            TvShowType.POPULAR -> getPopularTvShows()
        }
    }

    private fun getAiringTodayTvShows() {
        wrapperPager(
            data = { getAiringTodayTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessAiringTodayTvShows,
            onError = {
                onError(it.toErrorUiState())
            }
        )
    }

    private fun onSuccessAiringTodayTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.AIRING_TODAY,
                tvShowAiringToday = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    private fun getOnTheAirTvShows() {
        wrapperPager(
            data = { getOnTheAirTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessOnTheAirTvShows,
            onError = {
                onError(it.toErrorUiState())
            }
        )
    }

    private fun onSuccessOnTheAirTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.ON_THE_AIR,
                tvShowOnTheAir = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    private fun getPopularTvShows() {
        wrapperPager(
            data = { getPopularTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessPopularTvShows,
            onError = {
                onError(it.toErrorUiState())
            }
        )
    }

    private fun onSuccessPopularTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.POPULAR,
                tvShowPopular = showUiState,
                error = null,
                isLoading = false
            )
        }
    }


    private fun getTopRatedTvShows() {
        wrapperPager(
            data = { getTopRatedTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessTopRatedTvShows,
            onError = {
                onError(it.toErrorUiState())
            }
        )
    }

    private fun onSuccessTopRatedTvShows(showUiState: Flow<PagingData<ShowUiState>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.TOP_RATED,
                tvShowTopRated = showUiState,
                error = null,
                isLoading = false
            )
        }
    }

    /// endregion

    ///region error
    private fun onError(errorUiState: ErrorUiState) {
        _state.update {
            it.copy(
                error = errorUiState,
                isLoading = false
            )
        }
    }

    fun onPagingLoadStateChanged(
        loadStates: CombinedLoadStates
    ) {
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
                val errorUiState = (error as AppException).toErrorUiState()
                onError(errorUiState)
            }
        }
    }
}
