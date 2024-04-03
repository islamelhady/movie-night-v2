package com.elhady.movies.presentation.viewmodel.showmore

import androidx.lifecycle.SavedStateHandle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.bases.ListType
import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.core.domain.usecase.usecase.showmore.GetMorePopularMoviesByTypeUseCase
import com.elhady.movies.core.domain.usecase.usecase.showmore.GetMoreTopRatedByTypeUseCase
import com.elhady.movies.core.domain.usecase.usecase.showmore.GetMoreTrendingByTypeUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetAiringTodayTVShowsUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetOnTheAirTVShowsUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetPopularTVShowsUseCase
import com.elhady.movies.core.domain.usecase.usecase.tv_shows.GetTopRatedTVShowsUseCase
import com.elhady.movies.presentation.viewmodel.showmore.mappers.ShowMoreMovieUiMapper
import com.elhady.movies.presentation.viewmodel.showmore.mappers.ShowMoreTvShowUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    private val getShowMorePopularMoviesByTypeUseCase: GetMorePopularMoviesByTypeUseCase,
    private val getShowMoreTopRatedByTypeUseCase: GetMoreTopRatedByTypeUseCase,
    private val getShowMoreTrendingByTypeUseCase: GetMoreTrendingByTypeUseCase,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val getAiringTodayTVShowsUseCase: GetAiringTodayTVShowsUseCase,
    private val getPopularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getOnTheAirTVShowsUseCase: GetOnTheAirTVShowsUseCase,
    private val moviesMapper: ShowMoreMovieUiMapper,
    private val tvShowsMapper: ShowMoreTvShowUiMapper,
    savedStateHandle: SavedStateHandle,
    stringsRes: StringsRes
) : BaseViewModel<ShowMoreUiState, ShowMoreUiEvent>(
    ShowMoreUiState(
        showMoreType = savedStateHandle.get<ShowMoreType>(
            "showMoreType"
        ) ?: ShowMoreType.POPULAR_MOVIES,
        stringsRes = stringsRes
    )
), ShowMoreListener {

    init {
        _state.update { it.copy(isLoading = true) }
        getData()
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
            data = { getTopRatedTVShowsUseCase() },
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
                errorList = emptyList()
            )
        }
    }

    private fun getAiringTodayTvShow() {
        wrapperPager(
            data = { getAiringTodayTVShowsUseCase() },
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
                errorList = emptyList()
            )
        }
    }

    private fun getPopularTvShow() {
        wrapperPager(
            data = { getPopularTVShowsUseCase() },
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
                errorList = emptyList()
            )
        }
    }

    private fun getOnTheAirTvShow() {
        wrapperPager(
            data = { getOnTheAirTVShowsUseCase() },
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
                errorList = emptyList()
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
                errorList = emptyList()
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
                errorList = emptyList()
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
                errorList = emptyList()
            )
        }
    }

    // endregion MOVIES

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
                    it.copy(isLoading = false, errorList = listOf("no Network "))
                }
            }
        }
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEvent(ShowMoreUiEvent.ShowSnackBarEvent(messages))
    }

    override fun onClickBackNavigate() {
        sendEvent(ShowMoreUiEvent.BackNavigateEvent)
    }

    override fun onClickItem(mediaId: Int, type: ListType) {
        when(type){
            ListType.TV -> sendEvent(ShowMoreUiEvent.NavigateToTvShowDetailsEvent(mediaId))
            ListType.MOVIE -> sendEvent(ShowMoreUiEvent.NavigateToMovieDetailsEvent(mediaId))
        }
    }
}
