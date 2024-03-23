package com.elhady.movies.presentation.viewmodel.tvshows

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetAiringTodayTVShowsUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetOnTheAirTVShowsUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetPopularTVShowsUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetTopRatedTVShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    private val getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase,
    private val getOnTheAirTVShowsUseCase: GetOnTheAirTVShowsUseCase,
    private val getPopularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getGetTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val tvShowsMapper: TVShowsMapper
) : BaseViewModel<TVShowUIState, TVShowsUiEvent>(TVShowUIState()), TVShowsListener {


    init {
        getData()
    }

    ///region get data
    private fun getData() {
        try {
            viewModelScope.launch {
                when (_state.value.tvShowsType) {
                    TVShowsType.AIRING_TODAY -> getAiringTodayTVShows()
                    TVShowsType.ON_THE_AIR -> getOnTheAirTVShows()
                    TVShowsType.TOP_RATED -> getTopRatedTVShows()
                    TVShowsType.POPULAR -> getPopularTVShows()
                }
            }
        } catch (throwable: Throwable) {
            onError(throwable)
        }
    }

    fun getAiringTodayTVShows() {
        wrapperPager(
            data = { getAiringTodayTVShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessAiringTodayTVShows,
            onError = ::onError
        )
    }
    private fun onSuccessAiringTodayTVShows(tvShowsEntity: Flow<PagingData<TVShowsUI>>) {
        _state.update {
            it.copy(
                tvShowsType = TVShowsType.AIRING_TODAY,
                tvShowAiringToday = tvShowsEntity,
                errorList = emptyList(),
                isLoading = false
            )
        }
    }

    fun getOnTheAirTVShows() {
        wrapperPager(
            data = { getOnTheAirTVShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessOnTheAirTVShows,
            onError = ::onError
        )
    }
    private fun onSuccessOnTheAirTVShows(tvShowsEntity: Flow<PagingData<TVShowsUI>>) {
        _state.update {
            it.copy(
                tvShowsType = TVShowsType.ON_THE_AIR,
                tvShowOnTheAir = tvShowsEntity,
                errorList = emptyList(),
                isLoading = false
            )
        }
    }

    fun getPopularTVShows() {
        wrapperPager(
            data = { getPopularTVShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessPopularTVShows,
            onError = ::onError
        )
    }
    private fun onSuccessPopularTVShows(tvShowsEntity: Flow<PagingData<TVShowsUI>>) {
        _state.update {
            it.copy(
                tvShowsType = TVShowsType.POPULAR,
                tvShowPopular = tvShowsEntity,
                errorList = emptyList(),
                isLoading = false
            )
        }
    }


    fun getTopRatedTVShows() {
        wrapperPager(
            data = { getGetTopRatedTVShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessTopRatedTVShows,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedTVShows(tvShowsEntity: Flow<PagingData<TVShowsUI>>) {
        _state.update {
            it.copy(
                tvShowsType = TVShowsType.TOP_RATED,
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
        sendEvent(TVShowsUiEvent.ShowSnackBar(messages))
    }
    /// endregion

    ///region event
    override fun onClickTVShowItem(tvId: Int) {
        sendEvent(TVShowsUiEvent.NavigateToTVShowDetails(tvId))
    }

    override fun onClickScrollToTopScreen() {
        sendEvent(TVShowsUiEvent.ScrollToTopRecycler)
    }

    override fun onClickAiringTodayTVShowsResult() {
        sendEvent(TVShowsUiEvent.ShowAiringTodayTVShowsResult)
    }

    override fun onClickOnTheAiringTVShowsResult() {
        sendEvent(TVShowsUiEvent.ShowOnTheAirTVShowsResult)
    }

    override fun onClickTopRatedTVShowsResult() {
        sendEvent(TVShowsUiEvent.ShowTopRatedTVShowsResult)
    }

    override fun onClickPopularTVShowsResult() {
        sendEvent(TVShowsUiEvent.ShowPopularTVShowsResult)
    }
    /// endregion
}
