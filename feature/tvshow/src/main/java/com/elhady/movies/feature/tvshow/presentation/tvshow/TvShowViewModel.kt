package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.common.NoNetworkThrowable
import com.elhady.movies.core.common.ServerErrorThrowable
import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetOnTheAirTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetPopularTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTopRatedTvShowsUseCase
import com.elhady.movies.feature.tvshow.presentation.tvshow.mapper.TvShowUiMapper
import com.elhady.movies.core.ui.base.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase,
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val tvShowUiMapper: TvShowUiMapper
) : BaseViewModel<TvShowUiState, TvShowUiEvent>(TvShowUiState()), TvShowListener {


    init {
        getData()
    }

    ///region get data
    private fun getData() {
        viewModelScope.launch {
            when (_state.value.tvShowType) {
                TvShowType.AIRING_TODAY -> getAiringTodayTvShows()
                TvShowType.ON_THE_AIR -> getOnTheAirTvShows()
                TvShowType.TOP_RATED -> getTopRatedTvShows()
                TvShowType.POPULAR -> getPopularTvShows()
            }
        }
    }

    fun getAiringTodayTvShows() {
        wrapperPager(
            data = { getAiringTodayTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessAiringTodayTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessAiringTodayTvShows(tvShowsEntity: Flow<PagingData<TvShowUi>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.AIRING_TODAY,
                tvShowAiringToday = tvShowsEntity,
                error = null,
                isLoading = false
            )
        }
    }

    fun getOnTheAirTvShows() {
        wrapperPager(
            data = { getOnTheAirTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessOnTheAirTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessOnTheAirTvShows(tvShowsEntity: Flow<PagingData<TvShowUi>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.ON_THE_AIR,
                tvShowOnTheAir = tvShowsEntity,
                error = null,
                isLoading = false
            )
        }
    }

    fun getPopularTvShows() {
        wrapperPager(
            data = { getPopularTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessPopularTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessPopularTvShows(tvShowsEntity: Flow<PagingData<TvShowUi>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.POPULAR,
                tvShowPopular = tvShowsEntity,
                error = null,
                isLoading = false
            )
        }
    }


    fun getTopRatedTvShows() {
        wrapperPager(
            data = { getTopRatedTvShowsUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessTopRatedTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedTvShows(tvShowsEntity: Flow<PagingData<TvShowUi>>) {
        _state.update {
            it.copy(
                tvShowType = TvShowType.TOP_RATED,
                tvShowTopRated = tvShowsEntity,
                error = null,
                isLoading = false
            )
        }
    }

    /// endregion

    ///region error
    private fun onError(throwable: Throwable) {
        val uiError = throwable.toUiError()
        _state.update {
            it.copy(
                error = uiError,
                isLoading = false
            )
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
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
                val error = (combinedLoadStates.refresh as LoadState.Error).error
                _state.update {
                    it.copy(isLoading = false, error = error.toUiError())
                }
            }
        }
    }

    /// endregion

    ///region event
    override fun onClickTvShowItem(tvId: Int) {
        sendEvent(TvShowUiEvent.NavigateToTvShowDetails(tvId))
    }

    override fun onClickScrollToTopScreen() {
        sendEvent(TvShowUiEvent.ScrollToTopRecycler)
    }

    override fun onClickAiringTodayTvShowsResult() {
        getAiringTodayTvShows()
    }

    override fun onClickOnTheAirTvShowsResult() {
        getOnTheAirTvShows()
    }

    override fun onClickTopRatedTvShowsResult() {
        getTopRatedTvShows()
    }

    override fun onClickPopularTvShowsResult() {
        getPopularTvShows()
    }

    override fun onClickRetry() {
        getData()
    }
    /// endregion
}
