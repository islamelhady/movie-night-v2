package com.elhady.movies.presentation.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.domain.usecase.home.GetAiringTodayTvUseCase
import com.elhady.movies.core.domain.usecase.home.GetNowPlayingUseCase
import com.elhady.movies.core.domain.usecase.home.GetPopularMoviesUseCase
import com.elhady.movies.core.domain.usecase.home.GetPopularPeopleUseCase
import com.elhady.movies.core.domain.usecase.home.GetTopRatedUseCase
import com.elhady.movies.core.domain.usecase.home.GetTrendingMoviesUseCase
import com.elhady.movies.core.domain.usecase.home.GetTvShowUseCase
import com.elhady.movies.core.domain.usecase.home.GetUpcomingMoviesUseCase
import com.elhady.movies.presentation.viewmodel.home.mappers.AiringTodayUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.NowPlayingUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.PopularMoviesUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.PopularPeopleUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.TopRatedUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.TrendingUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.TvShowUiMapper
import com.elhady.movies.presentation.viewmodel.home.mappers.UpComingUiMapper
import com.elhady.movies.presentation.viewmodel.showmore.ShowMoreType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val upComingUiMapper: UpComingUiMapper,
    private val nowPlayingUiMapper: NowPlayingUiMapper,
    private val trendingUiMapper: TrendingUiMapper,
    private val topRatedUiMapper: TopRatedUiMapper,
    private val tvShowUiMapper: TvShowUiMapper,
    private val popularPeopleUiMapper: PopularPeopleUiMapper,
    private val popularMoviesUiMapper: PopularMoviesUiMapper,
    private val getAiringTodayTvUseCase: GetAiringTodayTvUseCase,
    private val airingTodayUiMapper: AiringTodayUiMapper
) : BaseViewModel<HomeUiState, HomeUiEvent>(HomeUiState()), HomeListener {


    /// region init
    init {
        getData()
        viewModelScope.launch { state.collectLatest {
            Log.d("HomeViewModel", "log(${this::class.java.simpleName}) : $it")
        }
        }

    }

    private fun getData() {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getUpComingMovies()
        getPopularPeople()
        getTvShow()
        getNowPlayingMovies()
        getTrendingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getAiringTodayTvShow()

    }

    private fun getAiringTodayTvShow() {
        tryToExecute(
            call = { getAiringTodayTvUseCase() },
            onSuccess = ::onSuccessAiringTodayTvShow,
            mapper = airingTodayUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessAiringTodayTvShow(airingTodayEntities: List<AiringTodayTvShowUiState>) {
        _state.update {
            it.copy(
                airingTodayTvShow = airingTodayEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
        Log.d("HomeViewModel", "log(${this::class.java.simpleName}) : ${this.state.value.airingTodayTvShow.isEmpty()}")

    }

    /// endregion

    /// region call
    private fun getPopularMovies() {
        tryToExecute(
            call = { popularMoviesUseCase() },
            onSuccess = ::onSuccessPopularMovies,
            mapper = popularMoviesUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessPopularMovies(popularMovieEntities: List<PopularMoviesUiState>) {
        _state.update {
            it.copy(
                popularMovies = popularMovieEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getTvShow() {
        tryToExecute(
            call = { tvShowUseCase() },
            onSuccess = ::onSuccessTvShow,
            mapper = tvShowUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTvShow(tvShowEntities: List<TvShowUiState>) {
        _state.update {
            it.copy(
                tvShow = tvShowEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getTopRatedMovies() {
        tryToExecute(
            call = { topRatedUseCase() },
            onSuccess = ::onSuccessTopRatedMovies,
            mapper = topRatedUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTopRatedMovies(topRatedMovieEntities: List<TopRatedUiState>) {
        _state.update {
            it.copy(
                topRated = topRatedMovieEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }


    private fun getUpComingMovies() {
        tryToExecute(
            call = { upcomingMoviesUseCase() },
            onSuccess = ::onSuccessUpcomingMovies,
            mapper = upComingUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessUpcomingMovies(upcomingMovieEntities: List<UpComingMoviesUiState>) {
        _state.update {
            it.copy(
                upComingMovies = upcomingMovieEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun getPopularPeople() {
        tryToExecute(
            call = { popularPeopleUseCase() },
            onSuccess = ::onSuccessPopularPeople,
            mapper = popularPeopleUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessPopularPeople(popularPeopleEntities: List<PopularPeopleUiState>) {
        _state.update {
            it.copy(
                popularPeople = popularPeopleEntities,
                isLoading = false, onErrors = emptyList()
            )
        }
    }

    private fun getNowPlayingMovies() {
        tryToExecute(
            call = { nowPlayingUseCase() },
            onSuccess = ::onSuccessNowPlayingMovies,
            mapper = nowPlayingUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessNowPlayingMovies(nowPlayingMovieEntities: List<NowPlayingUiState>) {
        _state.update {
            it.copy(
                nowPlayingMovies = nowPlayingMovieEntities,
                isLoading = false, onErrors = emptyList()
            )
        }
    }

    private fun getTrendingMovies() {
        tryToExecute(
            call = { trendingMoviesUseCase() },
            onSuccess = ::onSuccessTrendingMovies,
            mapper = trendingUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTrendingMovies(trendingMoviesEntities: List<TrendingMoviesUiState>) {
        _state.update {
            it.copy(
                trendingMovies = trendingMoviesEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }
    /// endregion

    /// region errors
    private fun onError(throwable: Throwable) {
        val errorMessage = throwable.message ?: "no network connection"
        showErrorWithSnackBar(errorMessage)
        _state.update { it.copy(onErrors = listOf(errorMessage), isLoading = false) }
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEvent(HomeUiEvent.ShowSnackBarEvent(messages))
    }
    /// endregion

    /// region events
    override fun onClickMovieItem(movieId: Int) {
        sendEvent(HomeUiEvent.MovieEvent(movieId))
    }

    override fun onClickTvShowItem(tvId: Int) {
        sendEvent(HomeUiEvent.TvShowEvent(tvId))
    }

    override fun onClickShowMore(showMoreType: ShowMoreType) {
        sendEvent(HomeUiEvent.ClickShowMoreEvent(showMoreType))
    }
    /// endregion



}