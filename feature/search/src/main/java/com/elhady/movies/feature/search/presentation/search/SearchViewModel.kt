package com.elhady.movies.feature.search.presentation.search

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.usecase.search.SearchMoviesUseCase
import com.elhady.movies.core.domain.usecase.search.SearchPeopleUseCase
import com.elhady.movies.core.domain.usecase.search.SearchTvsUseCase
import com.elhady.movies.core.domain.usecase.search.InsertSearchHistoryUseCase
import com.elhady.movies.core.domain.usecase.search.SearchHistoryUseCase
import com.elhady.movies.core.domain.usecase.movie.GetAllGenresMoviesUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetAllGenresTvsUseCase
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.core.ui.state.MovieHorizontalUiState
import com.elhady.movies.core.ui.state.PeopleUiState
import com.elhady.movies.feature.search.presentation.search.mapper.GenreUiMapper
import com.elhady.movies.feature.search.presentation.search.mapper.MovieUiMapper
import com.elhady.movies.feature.search.presentation.search.mapper.PeopleUiMapper
import com.elhady.movies.feature.search.presentation.search.mapper.TvUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllGenresMoviesUseCase: GetAllGenresMoviesUseCase,
    private val getAllGenresTvsUseCase: GetAllGenresTvsUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchTvsUseCase: SearchTvsUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val genreUiStateMapper: GenreUiMapper,
    private val movieUiMapper: MovieUiMapper,
    private val tvUiMapper: TvUiMapper,
    private val peopleUiMapper: PeopleUiMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()) {

    private var searchJob: kotlinx.coroutines.Job? = null

    init {
        viewModelScope.launch {
            state.map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(500)
                .collect { onSearchInputChanged(it) }
        }
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.QueryChanged -> onQueryChanged(event.query)
            SearchUiEvent.FilterClicked -> onClickFilter()
            is SearchUiEvent.GenreClicked -> onClickGenre(event.genreId)
            SearchUiEvent.ApplyFilterClicked -> getData()
            SearchUiEvent.ClearClicked -> onClearClicked()
            SearchUiEvent.MediaTypeMovieClicked -> onMediaTypeMovieClicked()
            SearchUiEvent.MediaTypeTvClicked -> onMediaTypeTvClicked()
            SearchUiEvent.MediaTypePeopleClicked -> onMediaTypePeopleClicked()
            SearchUiEvent.BackClicked -> sendEffect(SearchUiEffect.NavigateBack)
            SearchUiEvent.TryAgainClicked -> getData()
            is SearchUiEvent.MovieClicked -> sendEffect(SearchUiEffect.NavigateToMovieDetails(event.movieId))
            is SearchUiEvent.TvClicked -> sendEffect(SearchUiEffect.NavigateToTvDetails(event.tvId))
            is SearchUiEvent.PeopleClicked -> sendEffect(SearchUiEffect.NavigateToPeopleDetails(event.peopleId))
        }
    }

    private fun onQueryChanged(newQuery: String) {
        _state.update { it.copy(searchQuery = newQuery, isLoading = true) }
    }

    private fun onClearClicked() {
        _state.update { it.copy(searchQuery = "") }
    }

    private fun onMediaTypeMovieClicked() {
        _state.update {
            it.copy(
                selectedGenresId = null,
                mediaType = SearchUiState.SearchMedia.MOVIE,
                isLoading = true
            )
        }
        onSearchForMovie()
    }

    private fun onMediaTypeTvClicked() {
        _state.update {
            it.copy(
                selectedGenresId = null,
                mediaType = SearchUiState.SearchMedia.TV,
                isLoading = true
            )
        }
        onSearchForTv()
    }

    private fun onMediaTypePeopleClicked() {
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.PEOPLE,
                isLoading = true
            )
        }
        onSearchForPeople()
    }

    private fun onSearchInputChanged(newQuery: String) {
        viewModelScope.launch(ioDispatcher) {
            getSearchHistory(newQuery)
            getData()
        }
    }

    private suspend fun saveSearchHistoryInLocal(query: String) {
        if (query.isNotBlank()) {
            insertSearchHistoryUseCase(query)
        }
    }

    private suspend fun getSearchHistory(query: String) {
        val result = searchHistoryUseCase(query)
        _state.update { it.copy(searchHistory = result) }
    }

    private fun getData() {
        _state.update { it.copy(isLoading = true) }
        when (_state.value.mediaType) {
            SearchUiState.SearchMedia.MOVIE -> onSearchForMovie()
            SearchUiState.SearchMedia.TV -> onSearchForTv()
            SearchUiState.SearchMedia.PEOPLE -> onSearchForPeople()
        }
    }

    private fun onSearchForMovie() {
        searchJob?.cancel()
        searchJob = tryToExecute(
            call = {
                searchMoviesUseCase(
                    _state.value.searchQuery,
                    _state.value.selectedGenresId
                )
            },
            mapper = movieUiMapper,
            onSuccess = ::onSuccessMovies,
            onError = ::onError,
            dispatcher = ioDispatcher
        )
    }

    private fun onSuccessMovies(mediaUiState: List<MovieHorizontalUiState>) {
        val currentQuery = _state.value.searchQuery
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.MOVIE,
                searchMediaResult = mediaUiState,
                isSelectedPeople = false,
                isLoading = false,
                error = null,
            )
        }
        viewModelScope.launch(ioDispatcher) {
            saveSearchHistoryInLocal(currentQuery)
        }
    }

    private fun onSearchForTv() {
        searchJob?.cancel()
        searchJob = tryToExecute(
            call = {
                searchTvsUseCase(
                    _state.value.searchQuery,
                    _state.value.selectedGenresId
                )
            },
            mapper = tvUiMapper,
            onSuccess = ::onSuccessTv,
            onError = ::onError,
            dispatcher = ioDispatcher
        )
    }

    private fun onSuccessTv(tv: List<MovieHorizontalUiState>) {
        val currentQuery = _state.value.searchQuery
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.TV,
                searchMediaResult = tv,
                isSelectedPeople = false,
                isLoading = false,
                error = null
            )
        }
        viewModelScope.launch(ioDispatcher) {
            saveSearchHistoryInLocal(currentQuery)
        }
    }

    private fun onSearchForPeople() {
        searchJob?.cancel()
        searchJob = tryToExecute(
            call = { searchPeopleUseCase(_state.value.searchQuery) },
            mapper = peopleUiMapper,
            onSuccess = ::onSuccessPeople,
            onError = ::onError,
            dispatcher = ioDispatcher
        )
    }

    private fun onSuccessPeople(people: List<PeopleUiState>) {
        val currentQuery = _state.value.searchQuery
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.PEOPLE,
                searchPeopleResult = people,
                isSelectedPeople = true,
                isLoading = false,
                error = null
            )
        }
        viewModelScope.launch(ioDispatcher) {
            saveSearchHistoryInLocal(currentQuery)
        }
    }

    private fun onClickFilter() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (_state.value.mediaType) {
                SearchUiState.SearchMedia.MOVIE -> getAllGenresMovies()
                else -> getAllGenresTv()
            }
            sendEffect(SearchUiEffect.OpenFilterBottomSheet)
        }
    }

    private suspend fun getAllGenresMovies() {
        _state.update { it.copy(genres = emptyList()) }
        tryToExecute(
            call = { getAllGenresMoviesUseCase() },
            onSuccess = ::onSuccessGenres,
            onError = ::onError,
            dispatcher = ioDispatcher
        )
    }

    private suspend fun getAllGenresTv() {
        _state.update { it.copy(genres = emptyList()) }
        tryToExecute(
            call = { getAllGenresTvsUseCase() },
            onSuccess = ::onSuccessGenres,
            onError = ::onError,
            dispatcher = ioDispatcher
        )
    }

    private fun onSuccessGenres(genreEntities: List<Genre>) {
        _state.update {
            val updatedGenres =
                genreEntities.map { genre ->
                    genreUiStateMapper.map(
                        genre,
                        isSelected = genre.genreID == it.selectedGenresId
                    )
                }
            it.copy(
                genres = updatedGenres,
                isLoading = false,
                error = null,
            )
        }
    }

    private fun onClickGenre(genresId: Int) {
        val updatedGenres = _state.value.genres.map { genre ->
            genre.copy(isSelected = genre.genreId == genresId)
        }
        _state.update {
            it.copy(
                selectedGenresId = genresId,
                isLoading = false,
                genres = updatedGenres
            )
        }
    }

    private fun onError(throwable: AppException) {
        if (throwable == AppException.NoNetwork) {
            showErrorWithSnackBar(throwable.message ?: "No Network Connection")
        } else if (throwable == AppException.Timeout) {
            showErrorWithSnackBar(throwable.message ?: "time out!")
        }
        _state.update {
            it.copy(
                error = listOf(throwable.message ?: "No Network Connection"),
                isLoading = false
            )
        }
    }

    private fun showErrorWithSnackBar(messages: String) {
        sendEffect(SearchUiEffect.ShowSnackBar(messages))
    }
}
