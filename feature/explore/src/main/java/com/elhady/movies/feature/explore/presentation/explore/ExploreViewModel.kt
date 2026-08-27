package com.elhady.movies.feature.explore.presentation.explore

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.domain.usecase.movie.GetTrendingMoviesUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.feature.explore.presentation.explore.mapper.ExploreTrendingUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        _state.update {
            val newIsGridLayout = !it.isGridLayout
            it.copy(
                isGridLayout = newIsGridLayout,
                exploreItems = getExploreItems(it.trendingMoviesToday, newIsGridLayout)
            )
        }
    }

    private fun getTrendingMovies() {
        _state.update { it.copy(isLoading = true, errors = null) }
        viewModelScope.launch {
            tryToExecuteAsync(
                call = { trendingMoviesUseCase() },
                onSuccess = ::onSuccessTrendingMovies,
                mapper = trendingUiMapper,
                onError = { exception ->
                    onError(exception.toErrorUiState())
                }
            )
        }
    }

    private fun onSuccessTrendingMovies(trendingMoviesUiState: List<ExploreUiState.TrendingMoviesUiState>) {
        _state.update {
            it.copy(
                trendingMoviesToday = trendingMoviesUiState,
                exploreItems = getExploreItems(trendingMoviesUiState, it.isGridLayout),
                isLoading = false,
                errors = null
            )
        }
    }

    private fun getExploreItems(
        trendingMovies: List<ExploreUiState.TrendingMoviesUiState>,
        isGridLayout: Boolean
    ): List<ExploreItem> {
        return if (isGridLayout) {
            trendingMovies.map { ExploreItem.GridItem(it) }
        } else {
            trendingMovies.map { ExploreItem.HorizontalItem(it) }
        }
    }

    private fun onError(exception: ErrorUiState) {
        _state.update { it.copy(errors = exception, isLoading = false) }
        sendEffect(ExploreUiEffect.ShowSnackBar(messageRes = exception.messageRes))
    }

}
