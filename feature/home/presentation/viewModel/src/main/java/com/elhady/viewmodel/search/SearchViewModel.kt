package com.elhady.viewmodel.search

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.base.BaseViewModel
import com.elhady.movies.ui.search.SearchType
import com.elhady.movies.ui.search.SearchUiState
import com.elhady.usecase.search.GetAllSearchHistoryUseCase
import com.elhady.usecase.search.GetClearAllSearchHistoryUseCase
import com.elhady.usecase.search.GetClearSearchHistoryItemUseCase
import com.elhady.usecase.search.GetSearchForActorsUseCase
import com.elhady.usecase.search.GetSearchForMovieUseCase
import com.elhady.usecase.search.GetSearchForSeriesUseCase
import com.elhady.usecase.search.InsertSearchHistoryUseCase
import com.elhady.viewmodel.mappers.MediaUiMapper
import com.elhady.viewmodel.models.MediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForMovieUseCase: GetSearchForMovieUseCase,
    private val searchForSeriesUseCase: GetSearchForSeriesUseCase,
    private val searchForActorsUseCase: GetSearchForActorsUseCase,
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val getAllSearchHistoryUseCase: GetAllSearchHistoryUseCase,
    private val getClearAllSearchHistoryUseCase: GetClearAllSearchHistoryUseCase,
    private val getClearSearchHistoryItemUseCase: GetClearSearchHistoryItemUseCase,
    private val searchHistoryUiMapper: SearchHistoryUiMapper,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel<SearchUiState, SearchUiEvent>(SearchUiState()), MediaSearchInteractionListener,
    ActorSearchInteractionListener, HistoryInteractionListener {

    init {
        getAllSearchHistory()
    }

    override fun getData() {
//        _searchUiEvent.update { Event(SearchUiEvent.ClickRetryEvent) }

    }

    fun onSearchForMovies() {
        viewModelScope.launch {
            _state.update {
                val result = searchForMovieUseCase(movieQuery = it.inputSearch).map { pagingData ->
                    pagingData.map { mediaUiMapper.map(it) }
                }
                it.copy(
                    moviesSearchResult = result,
                    mediaType = SearchType.MOVIES,
                    isLoading = false
                )
            }
        }
    }

    fun onSearchForSeries() {
        viewModelScope.launch {
            _state.update {
                val result =
                    searchForSeriesUseCase(seriesQuery = it.inputSearch).map { pagingData ->
                        pagingData.map { mediaUiMapper.map(it) }
                    }
                it.copy(
                    moviesSearchResult = result,
                    mediaType = SearchType.TV,
                    isLoading = false
                )
            }


        }
    }

    fun onSearchForActors() {
        viewModelScope.launch {
            _state.update {
                val result = searchForActorsUseCase(it.inputSearch).map { pagingData ->
                    pagingData.map { mediaUiMapper.map(it) }
                }
                it.copy(
                    moviesSearchResult = result,
                    mediaType = SearchType.PEOPLE,
                    isLoading = false
                )
            }
        }
    }

    fun onClickInputSearch(searchInput: CharSequence) {
        _state.update { it.copy(inputSearch = searchInput.toString(), isLoading = true) }
        when (_state.value.mediaType) {
            SearchType.MOVIES -> onSearchForMovies()
            SearchType.TV -> onSearchForSeries()
            SearchType.PEOPLE -> onSearchForActors()
        }
        viewModelScope.launch {
            saveSearch(searchInput.toString())
        }
    }

    private fun getAllSearchHistory() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val list = getAllSearchHistoryUseCase()
                val result = list.map { item ->
                    searchHistoryUiMapper.map(item)
                }
                _state.update { it.copy(searchHistoryResult = result, isLoading = false) }
            }
    }

    override fun onClickMediaResult(media: MediaUiState) {
        sendEvent(SearchUiEvent.ClickMediaEvent(media))
    }

    override fun onClickActor(actorId: Int) {
        sendEvent(SearchUiEvent.ClickActorEvent(actorId))
    }

    fun onClickBack() {
        sendEvent(SearchUiEvent.ClickBackEvent)
    }

    fun onClickClearInputSearch(){
        getAllSearchHistory()
        _state.update { it.copy(inputSearch = "" )}
    }

    override fun onClickClearSearchHistoryItem(search: String){
        viewModelScope.launch {
            getClearSearchHistoryItemUseCase(search)
        }
        getAllSearchHistory()
    }

    fun onClickAllClearHistorySearch(){
        viewModelScope.launch {
            getClearAllSearchHistoryUseCase()
            _state.update { it.copy(searchHistoryResult = emptyList()) }
        }
    }

    private suspend fun saveSearch(name: String) {
            insertSearchHistoryUseCase(name)
    }

    override fun onClickHistorySearch(search: String) {
        onClickInputSearch(search)
    }

    fun setError(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.Error -> _state.update {
                it.copy(
                    isLoading = false,
                    onErrors = emptyList()
                )
            }

            LoadState.Loading -> _state.update {
                it.copy(
                    isLoading = true,
                    onErrors = emptyList()
                )
            }

            is LoadState.NotLoading -> _state.update {
                it.copy(
                    isLoading = false,
                    onErrors = emptyList()
                )
            }
        }
    }
}