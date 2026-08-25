package com.elhady.movies.feature.watchlist.presentation.myrated

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.account.GetMyRatedMoviesUseCase
import com.elhady.movies.core.domain.usecase.account.GetMyRatedTvShowUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.feature.watchlist.presentation.myrated.mapper.MyRatedMovieToMovieHorizontalUiMapper
import com.elhady.movies.feature.watchlist.presentation.myrated.mapper.MyRatedTvShowToMovieHorizontalUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyRatedViewModel @Inject constructor(
    private val getMyRatedTvShowUseCase: GetMyRatedTvShowUseCase,
    private val getMyRatedMoviesUseCase: GetMyRatedMoviesUseCase,
    private val myRatedMovieToMovieHorizontalUiMapper: MyRatedMovieToMovieHorizontalUiMapper,
    private val myRatedTvShowToMovieHorizontalUiMapper: MyRatedTvShowToMovieHorizontalUiMapper,
) : BaseViewModel<MyRatedUiState, MyRatedUiEffect>(
    MyRatedUiState()
) {

    init {
        getData()
    }

    fun onEvent(event: MyRatedUiEvent) {
        when (event) {

            MyRatedUiEvent.BackClicked -> {
                sendEffect(
                    MyRatedUiEffect.NavigateBack
                )
            }

            MyRatedUiEvent.MoviesSelected -> {
                fetchMyRatedMovies()
            }

            MyRatedUiEvent.TvShowsSelected -> {
                fetchMyRatedTvShows()
            }

            is MyRatedUiEvent.MediaClicked -> {
                when (state.value.rateType) {

                    RateType.Movies -> {
                        sendEffect(
                            MyRatedUiEffect.NavigateToMovieDetails(
                                movieId = event.id
                            )
                        )
                    }

                    RateType.TvShows -> {
                        sendEffect(
                            MyRatedUiEffect.NavigateToTvShowDetails(
                                tvShowId = event.id
                            )
                        )
                    }
                }
            }

            MyRatedUiEvent.RetryClicked -> {
                getData()
            }
        }
    }

    private fun getData() {
        when (state.value.rateType) {

            RateType.Movies -> {
                fetchMyRatedMovies()
            }

            RateType.TvShows -> {
                fetchMyRatedTvShows()
            }
        }
    }

    private fun fetchMyRatedMovies() {
        _state.update {
            it.copy(
                rateType = RateType.Movies,
                isLoading = true,
                error = null
            )
        }

        wrapperPager(
            data = {
                getMyRatedMoviesUseCase()
            },
            onSuccess = ::onRatedMoviesSuccess,
            mapper = myRatedMovieToMovieHorizontalUiMapper,
            onError = ::onError
        )
    }

    private fun fetchMyRatedTvShows() {
        _state.update {
            it.copy(
                rateType = RateType.TvShows,
                isLoading = true,
                error = null
            )
        }

        wrapperPager(
            data = {
                getMyRatedTvShowUseCase()
            },
            onSuccess = ::onRatedTvShowsSuccess,
            mapper = myRatedTvShowToMovieHorizontalUiMapper,
            onError = ::onError
        )
    }

    private fun onRatedMoviesSuccess(
        movies: Flow<PagingData<MovieHorizontalUiState>>
    ) {
        _state.update {
            it.copy(
                movies = movies,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onRatedTvShowsSuccess(
        tvShows: Flow<PagingData<MovieHorizontalUiState>>
    ) {
        _state.update {
            it.copy(
                movies = tvShows,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onError(
        throwable: AppException
    ) {
        _state.update {
            it.copy(
                isLoading = false,
                error = throwable.toErrorUiState()
            )
        }
    }

    fun setErrorUiState(
        combinedLoadStates: CombinedLoadStates
    ) {
        when (combinedLoadStates.refresh) {

            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }
            }

            LoadState.Loading -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        error = null
                    )
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = (combinedLoadStates.refresh as LoadState.Error).error.let { throwable ->
                            if (throwable is AppException) {
                                throwable.toErrorUiState()
                            } else null
                        }
                    )
                }
            }
        }
    }
}
