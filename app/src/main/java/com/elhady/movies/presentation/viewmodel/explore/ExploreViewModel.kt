package com.elhady.movies.presentation.viewmodel.explore

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.domain.usecase.home.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val trendingUiMapper: ExploreTrendingUiMapper
) : BaseViewModel<ExploreUiState, ExploreUiEvent>(ExploreUiState()), ExploreListener {

    val layout =  MutableStateFlow(false)
    init {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        viewModelScope.launch {
            layout.onEach {
                _state.update { it.copy(layoutManager = layout.value) }
            }.collect()
        }
        getTrendingMovies()
    }
    fun getTrendingMovies() {
        tryToExecute(
            call = { trendingMoviesUseCase() },
            onSuccess = ::onSuccessTrendingMovies,
            mapper = trendingUiMapper,
            onError = ::onError
        )
    }

    private fun onSuccessTrendingMovies(trendingMoviesEntities: List<ExploreUiState.TrendingMoviesUiState>) {
        _state.update {
            it.copy(
                trendingMoviesToday = trendingMoviesEntities,
                isLoading = false,
                onErrors = emptyList()
            )
        }
    }

    private fun onError(throwable: Throwable) {
        val errorMessage = throwable.message ?: "no network connection"
        showErrorWithSnackBar(errorMessage)
        _state.update { it.copy(onErrors = listOf(errorMessage), isLoading = false) }
    }

    private fun showErrorWithSnackBar(errorMessage: String) {
        sendEvent(ExploreUiEvent.ShowSnackBarMessageEvent(errorMessage))
    }


    override fun onClickSearch() {
        sendEvent(ExploreUiEvent.NavigateToSearchEvent)
    }


    override fun onClickMedia(id: Int) {
        sendEvent(ExploreUiEvent.NavigateToMovieDetailsEvent(movieId = id))
    }

}