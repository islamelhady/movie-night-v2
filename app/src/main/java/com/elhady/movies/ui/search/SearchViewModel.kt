package com.elhady.movies.ui.search

import androidx.paging.map
import com.elhady.movies.domain.usecases.search.GetSearchForMovieUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForMovieUseCase: GetSearchForMovieUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    private val _searchUiEvent = MutableStateFlow<Event<SearchUiEvent>?>(null)
    val searchUiEvent = _searchUiEvent.asStateFlow()



    override fun getData() {
        onSearchForMovies(searchUiState.value.inputSearch)

    }

    private fun onSearchForMovies(movie: String) {
        val result = searchForMovieUseCase(movieQuery = movie).map { pagingData ->
            pagingData.map {
                mediaUiMapper.map(it)
            }
        }
        _searchUiState.update {
            it.copy(moviesSearchResult = result)
        }
    }

    fun onClickInputSearch(searchInput: String){
        _searchUiState.update { it.copy(inputSearch = searchInput)}
        _searchUiEvent.update {
            Event(SearchUiEvent.ClickInputSearch(query = searchInput))
        }
    }

}