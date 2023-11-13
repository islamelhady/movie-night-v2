package com.elhady.movies.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.usecases.search.GetAllSearchHistoryUseCase
import com.elhady.movies.domain.usecases.search.GetSearchForActorsUseCase
import com.elhady.movies.domain.usecases.search.GetSearchForMovieUseCase
import com.elhady.movies.domain.usecases.search.GetSearchForSeriesUseCase
import com.elhady.movies.domain.usecases.search.PostSearchHistoryUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForMovieUseCase: GetSearchForMovieUseCase,
    private val searchForSeriesUseCase: GetSearchForSeriesUseCase,
    private val searchForActorsUseCase: GetSearchForActorsUseCase,
    private val postSearchHistoryUseCase: PostSearchHistoryUseCase,
    private val getAllSearchHistoryUseCase: GetAllSearchHistoryUseCase,
    private val searchHistoryUiMapper: SearchHistoryUiMapper,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel(), MediaSearchInteractionListener, ActorSearchInteractionListener, HistoryInteractionListener {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    private val _searchUiEvent = MutableStateFlow<Event<SearchUiEvent>?>(null)
    val searchUiEvent = _searchUiEvent.asStateFlow()


    init {
        getAllSearchHistory()
    }

    override fun getData() {
//        _searchUiEvent.update { Event(SearchUiEvent.ClickRetryEvent) }

    }

    fun onSearchForMovies() {
        viewModelScope.launch {
            _searchUiState.update {
                val result = searchForMovieUseCase(movieQuery = it.inputSearch).map { pagingData ->
                    pagingData.map { mediaUiMapper.map(it) }
                }
                it.copy(
                    moviesSearchResult = result,
                    mediaType = MediaTypes.MOVIES,
                    isLoading = false
                )
            }
        }
    }

    fun onSearchForSeries() {
        viewModelScope.launch {
            _searchUiState.update {
                val result =
                    searchForSeriesUseCase(seriesQuery = it.inputSearch).map { pagingData ->
                        pagingData.map { mediaUiMapper.map(it) }
                    }
                it.copy(
                    moviesSearchResult = result,
                    mediaType = MediaTypes.SERIES,
                    isLoading = false
                )
            }


        }
    }

    fun onSearchForActors() {
        viewModelScope.launch {
            _searchUiState.update {
                val result = searchForActorsUseCase(it.inputSearch).map { pagingData ->
                    pagingData.map { mediaUiMapper.map(it) }
                }
                it.copy(
                    moviesSearchResult = result,
                    mediaType = MediaTypes.ACTORS,
                    isLoading = false
                )
            }
        }
    }

    fun onClickInputSearch(searchInput: CharSequence) {
        _searchUiState.update { it.copy(inputSearch = searchInput.toString()) }
        when (_searchUiState.value.mediaType) {
            MediaTypes.MOVIES -> onSearchForMovies()
            MediaTypes.SERIES -> onSearchForSeries()
            MediaTypes.ACTORS -> onSearchForActors()
        }

    }

    private fun getAllSearchHistory() {
        viewModelScope.launch {
            getAllSearchHistoryUseCase().collect { list ->
                val result = list.map { item ->
                    searchHistoryUiMapper.map(item)
                }
                _searchUiState.update { it.copy(searchHistoryResult = result, isLoading = false) }
            }
        }
    }

    override fun onClickMediaResult() {
        TODO("Not yet implemented")
    }

    override fun onClickActor(actorId: Int) {
        saveSearch(actorId, "islam")
        _searchUiEvent.update {
            Event(SearchUiEvent.ClickActorEvent(actorId))
        }
    }

    fun onClickBack() {
        _searchUiEvent.update {
            Event(SearchUiEvent.ClickBackEvent)
        }
    }

    private fun saveSearch(id: Int, name: String) {
        viewModelScope.launch {
            postSearchHistoryUseCase(id, name)
        }
    }

    override fun onClickHistorySearch() {
        TODO("Not yet implemented")
    }

    fun setError(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.Error -> _searchUiState.update {
                it.copy(
                    isLoading = false,
                    error = emptyList()
                )
            }

            LoadState.Loading -> _searchUiState.update {
                it.copy(
                    isLoading = true,
                    error = emptyList()
                )
            }

            is LoadState.NotLoading -> _searchUiState.update {
                it.copy(
                    isLoading = false,
                    error = emptyList()
                )
            }
        }


    }
}