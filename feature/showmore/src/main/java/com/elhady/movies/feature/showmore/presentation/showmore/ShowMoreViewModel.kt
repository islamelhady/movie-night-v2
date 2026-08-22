package com.elhady.movies.feature.showmore.presentation.showmore

import androidx.lifecycle.SavedStateHandle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.model.account.ListType
import com.elhady.movies.core.common.ShowMoreType
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.core.domain.usecase.movie.GetMorePopularMoviesByTypeUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMoreTopRatedByTypeUseCase
import com.elhady.movies.core.domain.usecase.movie.GetMoreTrendingByTypeUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetOnTheAirTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetPopularTvShowsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTopRatedTvShowsUseCase
import com.elhady.movies.feature.showmore.presentation.showmore.mapper.ShowMoreMovieUiMapper
import com.elhady.movies.feature.showmore.presentation.showmore.mapper.ShowMoreTvShowUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    private val getShowMorePopularMoviesByTypeUseCase: GetMorePopularMoviesByTypeUseCase,
    private val getShowMoreTopRatedByTypeUseCase: GetMoreTopRatedByTypeUseCase,
    private val getShowMoreTrendingByTypeUseCase: GetMoreTrendingByTypeUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val getAiringTodayTvShowsUseCase: GetAiringTodayTvShowsUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase,
    private val moviesMapper: ShowMoreMovieUiMapper,
    private val tvShowsMapper: ShowMoreTvShowUiMapper,
    savedStateHandle: SavedStateHandle,
    stringsRes: StringsRes
) : BaseViewModel<ShowMoreUiState, ShowMoreUiEffect>(
    ShowMoreUiState(
        showMoreType = savedStateHandle.get<ShowMoreType>(
            "showMoreType"
        ) ?: ShowMoreType.POPULAR_MOVIES,
        stringsRes = stringsRes
    )
) {

    init {
        _state.update { it.copy(isLoading = true) }
        getData()
    }

    fun onEvent(event: ShowMoreUiEvent) {
        when (event) {
            ShowMoreUiEvent.BackClicked -> sendEffect(ShowMoreUiEffect.NavigateBack)
            is ShowMoreUiEvent.ItemClicked -> {
                when (event.type) {
                    ListType.TV -> sendEffect(ShowMoreUiEffect.NavigateToTvShowDetails(event.id))
                    ListType.MOVIE -> sendEffect(ShowMoreUiEffect.NavigateToMovieDetails(event.id))
                }
            }

            ShowMoreUiEvent.RetryClicked -> getData()
        }
    }

    fun getData() {
        when (_state.value.showMoreType) {
            ShowMoreType.POPULAR_MOVIES -> getPopularMoviesShowMore()
            ShowMoreType.TOP_RATED_MOVIES -> getTopRatedMoviesShowMore()
            ShowMoreType.TRENDING_MOVIES -> getTrendingMoviesShowMore()
            ShowMoreType.AIRING_TODAY_TV -> getAiringTodayTvShow()
            ShowMoreType.TOP_RATED_TV -> getTopRatedTvShow()
            ShowMoreType.POPULAR_TV -> getPopularTvShow()
            ShowMoreType.ON_THE_AIR_TV -> getOnTheAirTvShow()
        }
    }

    // region TV SHOW

    private fun getTopRatedTvShow() {
        wrapperPager(
            data = { getTopRatedTvShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessTopRatedTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedTvShows(tvShowsEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreTopRatedTvShow = tvShowsEntity,
                isLoading = false,
                showMoreType = ShowMoreType.TOP_RATED_TV,
                errors = null
            )
        }
    }

    private fun getAiringTodayTvShow() {
        wrapperPager(
            data = { getAiringTodayTvShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessAiringTodayTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessAiringTodayTvShows(tvShowsEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreAiringTodayTvShow = tvShowsEntity,
                isLoading = false,
                showMoreType = ShowMoreType.AIRING_TODAY_TV,
                errors = null
            )
        }
    }

    private fun getPopularTvShow() {
        wrapperPager(
            data = { getPopularTvShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessPopularTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessPopularTvShows(tvShowsEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMorePopularTvShow = tvShowsEntity,
                isLoading = false,
                showMoreType = ShowMoreType.POPULAR_TV,
                errors = null
            )
        }
    }

    private fun getOnTheAirTvShow() {
        wrapperPager(
            data = { getOnTheAirTvShowsUseCase() },
            mapper = tvShowsMapper,
            onSuccess = ::onSuccessOnTheAirTvShows,
            onError = ::onError
        )
    }

    private fun onSuccessOnTheAirTvShows(tvShowsEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreOnTheAirTvShow = tvShowsEntity,
                isLoading = false,
                showMoreType = ShowMoreType.ON_THE_AIR_TV,
                errors = null
            )
        }
    }


    // endregion TV SHOW

    // region MOVIES
    private fun getPopularMoviesShowMore() {
        wrapperPager(
            data = { getShowMorePopularMoviesByTypeUseCase() },
            mapper = moviesMapper,
            onSuccess = ::onSuccessPopularMovies,
            onError = ::onError
        )
    }

    private fun onSuccessPopularMovies(movieEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreType = ShowMoreType.POPULAR_MOVIES,
                showMorePopularMovies = movieEntity,
                isLoading = false,
                errors = null
            )
        }
    }

    private fun getTopRatedMoviesShowMore() {
        wrapperPager(
            data = { getShowMoreTopRatedByTypeUseCase() },
            mapper = moviesMapper,
            onSuccess = ::onSuccessTopRatedMovies,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedMovies(movieEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreType = ShowMoreType.TOP_RATED_MOVIES,
                showMoreTopRatedMovies = movieEntity,
                isLoading = false,
                errors = null
            )
        }
    }

    private fun getTrendingMoviesShowMore() {
        wrapperPager(
            data = { getShowMoreTrendingByTypeUseCase() },
            mapper = moviesMapper,
            onSuccess = ::onSuccessTrendingMovies,
            onError = ::onError
        )
    }

    private fun onSuccessTrendingMovies(movieEntity: Flow<PagingData<ShowMoreUi>>) {
        _state.update {
            it.copy(
                showMoreType = ShowMoreType.TRENDING_MOVIES,
                showMoreTrendingMovies = movieEntity,
                isLoading = false,
                errors = null
            )
        }
    }

    // endregion MOVIES

    private fun onError(appException: AppException) {
        _state.update {
            it.copy(
                errors = appException.toErrorUiState(),
                isLoading = false
            )
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, errors = null)
                }
            }

            LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, errors = null)
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, errors = (combinedLoadStates.refresh as LoadState.Error).error.let { th ->
                        if (th is AppException) th.toErrorUiState() else null
                    })
                }
            }
        }
    }
}
