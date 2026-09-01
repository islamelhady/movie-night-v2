package com.elhady.movies.feature.home.presentation.home

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.usecase.movie.GetNowPlayingUseCase
import com.elhady.movies.core.domain.usecase.movie.GetPopularMoviesUseCase
import com.elhady.movies.core.domain.usecase.movie.GetTopRatedUseCase
import com.elhady.movies.core.domain.usecase.movie.GetTrendingMoviesUseCase
import com.elhady.movies.core.domain.usecase.movie.GetUpcomingMoviesUseCase
import com.elhady.movies.core.domain.usecase.people.GetPopularPeopleUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetAiringTodayTvUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.home.presentation.home.mapper.AiringTodayUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.NowPlayingUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.PopularMoviesUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.PopularPeopleUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.TopRatedUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.TrendingUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.TvShowUiMapper
import com.elhady.movies.feature.home.presentation.home.mapper.UpComingUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nowPlayingUseCase: GetNowPlayingUseCase,
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularPeopleUseCase: GetPopularPeopleUseCase,
    private val topRatedUseCase: GetTopRatedUseCase,
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val tvShowUseCase: GetTvShowUseCase,
    private val getAiringTodayTvUseCase: GetAiringTodayTvUseCase,

    private val upComingUiMapper: UpComingUiMapper,
    private val nowPlayingUiMapper: NowPlayingUiMapper,
    private val trendingUiMapper: TrendingUiMapper,
    private val topRatedUiMapper: TopRatedUiMapper,
    private val tvShowUiMapper: TvShowUiMapper,
    private val popularPeopleUiMapper: PopularPeopleUiMapper,
    private val popularMoviesUiMapper: PopularMoviesUiMapper,
    private val airingTodayUiMapper: AiringTodayUiMapper,
    private val stringsRes: StringsRes,
) : BaseViewModel<HomeUiState, HomeUiEffect>(
    HomeUiState()
) {

    init {
        getData()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {

            is HomeUiEvent.MovieClicked -> {
                sendEffect(
                    HomeUiEffect.NavigateToMovieDetails(
                        movieId = event.movieId
                    )
                )
            }

            is HomeUiEvent.TvShowClicked -> {
                sendEffect(
                    HomeUiEffect.NavigateToTvShowDetails(
                        tvShowId = event.tvShowId
                    )
                )
            }

            is HomeUiEvent.ShowMoreClicked -> {
                sendEffect(
                    HomeUiEffect.NavigateToShowMore(event.type)
                )
            }

            HomeUiEvent.RetryClicked -> {
                getData()
            }

            HomeUiEvent.Refresh -> {
                getData()
            }

            is HomeUiEvent.PeopleClicked -> {
                sendEffect(
                    HomeUiEffect.NavigateToPeopleDetails(
                        personId = event.personId
                    )
                )
            }
        }
    }
    private fun getData() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            supervisorScope {
                launch {
                    getUpcomingMovies()
                }

                launch {
                    getPopularPeople()
                }

                launch {
                    getTvShow()
                }

                launch {
                    getNowPlayingMovies()
                }

                launch {
                    getTrendingMovies()
                }

                launch {
                    getPopularMovies()
                }

                launch {
                    getTopRatedMovies()
                }

                launch {
                    getAiringTodayTvShow()
                }
            }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private suspend fun getUpcomingMovies() {
        tryToExecuteAsync(
            call = { upcomingMoviesUseCase() },
            mapper = upComingUiMapper,
            onSuccess = ::onSuccessUpcomingMovies,
            onError = ::onError
        )
    }

    private suspend fun getPopularPeople() {
        tryToExecuteAsync(
            call = { popularPeopleUseCase() },
            mapper = popularPeopleUiMapper,
            onSuccess = ::onSuccessPopularPeople,
            onError = ::onError
        )
    }

    private suspend fun getTvShow() {
        tryToExecuteAsync(
            call = { tvShowUseCase() },
            mapper = tvShowUiMapper,
            onSuccess = ::onSuccessTvShow,
            onError = ::onError
        )
    }

    private suspend fun getNowPlayingMovies() {
        tryToExecuteAsync(
            call = { nowPlayingUseCase() },
            mapper = nowPlayingUiMapper,
            onSuccess = ::onSuccessNowPlayingMovies,
            onError = ::onError
        )
    }

    private suspend fun getTrendingMovies() {
        tryToExecuteAsync(
            call = { trendingMoviesUseCase() },
            mapper = trendingUiMapper,
            onSuccess = ::onSuccessTrendingMovies,
            onError = ::onError
        )
    }

    private suspend fun getPopularMovies() {
        tryToExecuteAsync(
            call = { popularMoviesUseCase() },
            mapper = popularMoviesUiMapper,
            onSuccess = ::onSuccessPopularMovies,
            onError = ::onError
        )
    }

    private suspend fun getTopRatedMovies() {
        tryToExecuteAsync(
            call = { topRatedUseCase() },
            mapper = topRatedUiMapper,
            onSuccess = ::onSuccessTopRatedMovies,
            onError = ::onError
        )
    }

    private suspend fun getAiringTodayTvShow() {
        tryToExecuteAsync(
            call = { getAiringTodayTvUseCase() },
            mapper = airingTodayUiMapper,
            onSuccess = ::onSuccessAiringTodayTvShow,
            onError = ::onError
        )
    }

    private fun onSuccessUpcomingMovies(
        movies: List<UpcomingMovieUiState>
    ) {
        _state.update {
            it.copy(upcomingMovies = movies)
        }
    }

    private fun onSuccessPopularPeople(
        people: List<PopularPeopleUiState>
    ) {
        _state.update {
            it.copy(popularPeople = people)
        }
    }

    private fun onSuccessTvShow(
        tvShows: List<TvShowUiState>
    ) {
        _state.update {
            it.copy(tvShows = tvShows)
        }
    }

    private fun onSuccessNowPlayingMovies(
        movies: List<NowPlayingMovieUiState>
    ) {
        _state.update {
            it.copy(nowPlayingMovies = movies)
        }
    }

    private fun onSuccessTrendingMovies(
        movies: List<TrendingMovieUiState>
    ) {
        _state.update {
            it.copy(trendingMovies = movies)
        }
    }

    private fun onSuccessPopularMovies(
        movies: List<PopularMovieUiState>
    ) {
        _state.update {
            it.copy(popularMovies = movies)
        }
    }

    private fun onSuccessTopRatedMovies(
        movies: List<TopRatedMovieUiState>
    ) {
        _state.update {
            it.copy(topRatedMovies = movies)
        }
    }

    private fun onSuccessAiringTodayTvShow(
        tvShows: List<AiringTodayTvShowUiState>
    ) {
        _state.update {
            it.copy(airingTodayTvShows = tvShows)
        }
    }

    private fun onError(error: AppException) {
        val message = when (error) {
            is AppException.NoNetwork -> stringsRes.noNetworkConnection
            is AppException.Timeout -> stringsRes.timeOut
            else -> stringsRes.someThingError
        }

        if (state.value.hasData) {
            sendEffect(HomeUiEffect.ShowSnackBar(message = message))
        } else {
            _state.update {
                it.copy(
                    error = error.toErrorUiState(),
                    isLoading = false
                )
            }
        }
    }
}
