package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTVShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetOnTheAirTVShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetPopularTVShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTopRatedTVShowsUseCase
import com.elhady.movies.feature.tvshow.presentation.tvshow.mapper.TvShowUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase,
    private val getOnTheAirTVShowsUseCase: GetOnTheAirTVShowsUseCase,
    private val getPopularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getGetTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val tvShowUiMapper: TvShowUiMapper
) : BaseViewModel<TvShowUiState, TvShowUiEvent>(TvShowUiState()), TvShowListener {


    init {
        getData()
    }

    ///region get data
    private fun getData() {
        try {
            viewModelScope.launch {
                when (_state.value.tvShowType) {
                    TvShowType.AIRING_TODAY -> getAiringTodayTvShows()
                    TvShowType.ON_THE_AIR -> getOnTheAirTvShows()
                    TvShowType.TOP_RATED -> getTopRatedTvShows()
                    TvShowType.POPULAR -> getPopularTvShows()
                }
            }
        } catch (throwable: Throwable) {
            onError(throwable)
        }
    }

    fun getAiringTodayTvShows() {
        wrapperPager(
            data = { getAiringTodayTVShowsUseCase() },
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
                errorList = emptyList(),
                isLoading = false
            )
        }
    }

    fun getOnTheAirTvShows() {
        wrapperPager(
            data = { getOnTheAirTVShowsUseCase() },
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
                errorList = emptyList(),
                isLoading = false
            )
        }
    }

    fun getPopularTvShows() {
        wrapperPager(
            data = { getPopularTVShowsUseCase() },
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
                errorList = emptyList(),
                isLoading = false
            )
        }
    }


    fun getTopRatedTvShows() {
        wrapperPager(
            data = { getGetTopRatedTVShowsUseCase() },
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
                errorList = emptyList(),
                isLoading = false
            )
        }
    }

    /// endregion

    ///region error
    private fun onError(throwable: Throwable) {
        val errorMessage = throwable.message ?: "No network connection"
        showErrorWithSnackBar(errorMessage)
        _state.update {
            it.copy(
                errorList = listOf(errorMessage),
                isLoading = false
            )
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, errorList = emptyList())
                }
            }

            LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, errorList = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, errorList = listOf("no Network"))
                }
            }
        }
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEvent(TvShowUiEvent.ShowSnackBar(messages))
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
        sendEvent(TvShowUiEvent.ShowAiringTodayTvShowsResult)
    }

    override fun onClickOnTheAirTvShowsResult() {
        sendEvent(TvShowUiEvent.ShowOnTheAirTvShowsResult)
    }

    override fun onClickTopRatedTvShowsResult() {
        sendEvent(TvShowUiEvent.ShowTopRatedTvShowsResult)
    }

    override fun onClickPopularTvShowsResult() {
        sendEvent(TvShowUiEvent.ShowPopularTvShowsResult)
    }
    /// endregion
}
