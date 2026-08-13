package com.elhady.movies.feature.explore.presentation.explore

import com.elhady.movies.core.domain.usecase.movie.GetTrendingMoviesUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.messageRes
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.explore.presentation.explore.mapper.ExploreTrendingUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val trendingUiMapper: ExploreTrendingUiMapper
) : BaseViewModel<ExploreUiState, ExploreUiEffect>(ExploreUiState()) {

    init {
        onEvent(ExploreUiEvent.RetryClicked)
    }

    fun onEvent(event: ExploreUiEvent) {
        when (event) {
            is ExploreUiEvent.ChangeLayoutClicked -> {
                changeLayout()
            }

            is ExploreUiEvent.MovieClicked -> {
                sendEffect(ExploreUiEffect.NavigateToMovieDetails(movieId = event.id))
            }

            ExploreUiEvent.SearchClicked -> {
                sendEffect(ExploreUiEffect.NavigateToSearch)
            }

            ExploreUiEvent.RetryClicked -> {
                getTrendingMovies()
            }
        }
    }

    private fun changeLayout() {
        _state.update { it.copy(isGridLayout = !it.isGridLayout) }
    }

    private fun getTrendingMovies() {
        _state.update { it.copy(isLoading = true, errors = null) }
        tryToExecute(
            call = { trendingMoviesUseCase() },
            onSuccess = ::onSuccessTrendingMovies,
            mapper = trendingUiMapper,
            onError = { exception ->
                onError(exception.toErrorUiState())
            }
        )
    }

    private fun onSuccessTrendingMovies(trendingMoviesUiState: List<ExploreUiState.TrendingMoviesUiState>) {
        _state.update {
            it.copy(
                trendingMoviesToday = trendingMoviesUiState,
                isLoading = false,
                errors = null
            )
        }
    }

    private fun onError(exception: ErrorUiState) {
        _state.update { it.copy(errors = exception, isLoading = false) }
        sendEffect(ExploreUiEffect.ShowSnackBar(message = exception.messageRes.toString()))
    }

}
