package com.elhady.movies.presentation.viewmodel.search

import androidx.lifecycle.viewModelScope
import com.elhady.movies.core.bases.BaseViewModel
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.usecase.repository.NoNetworkThrowable
import com.elhady.movies.core.domain.usecase.usecase.search.SearchMoviesUseCase
import com.elhady.movies.core.domain.usecase.usecase.search.SearchPeopleUseCase
import com.elhady.movies.core.domain.usecase.usecase.search.SearchTvsUseCase
import com.elhady.movies.core.domain.usecase.usecase.searchhistory.InsertSearchHistoryUseCase
import com.elhady.movies.core.domain.usecase.usecase.searchhistory.SearchHistoryUseCase
import com.elhady.movies.core.domain.usecase.usecase.GetAllGenresMoviesUseCase
import com.elhady.movies.core.domain.usecase.usecase.GetAllGenresTvsUseCase
import com.elhady.movies.presentation.viewmodel.common.model.MovieHorizontalUIState
import com.elhady.movies.presentation.viewmodel.common.model.PeopleUIState
import com.elhady.movies.presentation.viewmodel.search.mappers.GenreUiStateMapper
import com.elhady.movies.presentation.viewmodel.search.mappers.MovieUiMapper
import com.elhady.movies.presentation.viewmodel.search.mappers.PeopleUiMapper
import com.elhady.movies.presentation.viewmodel.search.mappers.TvUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
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
    private val genreUiStateMapper: GenreUiStateMapper,
    private val movieUiMapper: MovieUiMapper,
    private val tvUiMapper: TvUiMapper,
    private val peopleUiMapper: PeopleUiMapper
) : BaseViewModel<SearchUiState, SearchUiEvent>(SearchUiState()), SearchListener {

    val query = MutableStateFlow("")

    //region init
    init {
        viewModelScope.launch {
            query.onEach {
                _state.update { it.copy(isLoading = true) }
            }.debounce(500)
                .collect { onSearchInputChanged(it) }
        }
    }
    // endregion

    //region search input
    private fun onSearchInputChanged(newQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveSearchHistoryInLocal(newQuery)
            getSearchHistory(newQuery)
            getData()
        }
    }
    // endregion

    //region search history
    private suspend fun saveSearchHistoryInLocal(query: String) {
        insertSearchHistoryUseCase(query)
    }

    private suspend fun getSearchHistory(query: String) {
        val result = searchHistoryUseCase(query)
        _state.update { it.copy(searchHistory = result) }
    }
    // endregion

    // region get data
    fun getData() {
        _state.update { it.copy(isLoading = true) }
        when (_state.value.mediaType) {
            SearchUiState.SearchMedia.MOVIE -> onSearchForMovie()
            SearchUiState.SearchMedia.TV -> onSearchForTv()
            SearchUiState.SearchMedia.PEOPLE -> onSearchForPeople()
        }
    }

    // endregion
    // region movie
    fun onSearchForMovie() {
        tryToExecute(
            call = {
                searchMoviesUseCase(
                    query.value,
                    _state.value.selectedGenresId
                )
            },
            mapper = movieUiMapper,
            onSuccess = ::onSuccessMovies,
            onError = ::onError,
        )
    }

    private fun onSuccessMovies(mediaUIState: List<MovieHorizontalUIState>) {
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.MOVIE,
                searchMediaResult = mediaUIState,
                isSelectedPeople = false,
                isLoading = false,
                error = null,
            )
        }
    }
    // endregion

    // region tv
    fun onSearchForTv() {
        tryToExecute(
            call = {
                searchTvsUseCase(
                    query.value,
                    _state.value.selectedGenresId
                )
            },
            mapper = tvUiMapper,
            onSuccess = ::onSuccessTv,
            onError = ::onError
        )
    }

    private fun onSuccessTv(tv: List<MovieHorizontalUIState>) {
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.TV,
                searchMediaResult = tv,
                isSelectedPeople = false,
                isLoading = false,
                error = null
            )
        }
    }
    // endregion

    // region people
    fun onSearchForPeople() {
        tryToExecute(
            call = { searchPeopleUseCase(query.value) },
            mapper = peopleUiMapper,
            onSuccess = ::onSuccessPeople,
            onError = ::onError
        )
    }

    private fun onSuccessPeople(people: List<PeopleUIState>) {
        _state.update {
            it.copy(
                mediaType = SearchUiState.SearchMedia.PEOPLE,
                searchPeopleResult = people,
                isSelectedPeople = true,
                isLoading = false,
                error = null
            )
        }
    }
    // endregion

    ///region events
    override fun onClickFilter() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (_state.value.mediaType) {
                SearchUiState.SearchMedia.MOVIE -> getAllGenresMovies()
                else -> getAllGenresTv()
            }
            sendEvent(SearchUiEvent.OpenFilterBottomSheet)
        }
    }

    private suspend fun getAllGenresMovies() {
        _state.update { it.copy(genres = emptyList()) }
        tryToExecute(
            call = { getAllGenresMoviesUseCase() },
            onSuccess = ::onSuccessGenres,
            onError = ::onError
        )
    }

    private suspend fun getAllGenresTv() {
        _state.update { it.copy(genres = emptyList()) }
        tryToExecute(
            call = { getAllGenresTvsUseCase() },
            onSuccess = ::onSuccessGenres,
            onError = ::onError
        )
    }


    private fun onSuccessGenres(genreEntities: List<GenreEntity>) {
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

    override fun onClickGenre(genresId: Int) {
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

    override fun onClickClear() {
        query.value = ""
    }

    override fun showResultMovie() {
        sendEvent(SearchUiEvent.ShowMovieResult)
        _state.update {
            it.copy(
                selectedGenresId = null,
                mediaType = SearchUiState.SearchMedia.MOVIE
            )
        }
    }

    override fun showResultTv() {
        sendEvent(SearchUiEvent.ShowTvResult)
        _state.update {
            it.copy(
                selectedGenresId = null,
                mediaType = SearchUiState.SearchMedia.TV
            )
        }
    }

    override fun showResultPeople() {
        sendEvent(SearchUiEvent.ShowPeopleResult)
        _state.update { it.copy(mediaType = SearchUiState.SearchMedia.PEOPLE) }
    }

    override fun onClickBack() {
        sendEvent(SearchUiEvent.NavigateToBack)
    }

    override fun onClickMedia(id: Int) {
        when (_state.value.mediaType) {
            SearchUiState.SearchMedia.MOVIE -> sendEvent(SearchUiEvent.NavigateToMovie(id))
            else -> sendEvent(SearchUiEvent.NavigateToTv(id))
        }
    }

    override fun onClickPeople(id: Int) {
        sendEvent(SearchUiEvent.NavigateToPeople(id))
    }
    ///endregion


    /// region error handling
    private fun onError(throwable: Throwable) {
        if (throwable == NoNetworkThrowable()) {
            showErrorWithSnackBar(throwable.message ?: "No Network Connection")
        } else if (throwable == SocketTimeoutException()) {
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
        sendEvent(SearchUiEvent.ShowSnackBar(messages))
    }
    //endregion
}