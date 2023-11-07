package com.elhady.movies.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.usecases.GetGenreMovieUseCase
import com.elhady.movies.domain.usecases.GetCategoryByGenreUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.movieDetails.ErrorUiState
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoviesByGenreIDUseCase: GetCategoryByGenreUseCase,
    private val getGenreMovieUseCase: GetGenreMovieUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel(), MediaInteractionListener, CategoryInteractionListener {

//    val args = CategoryFragmentArgs.fromSavedStateHandle(savedStateHandle)


    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState = _categoryUiState.asStateFlow()

    private val _categoryUiEvent =  MutableStateFlow<Event<CategoryUiEvent>?>(null)
    val categoryUiEvent = _categoryUiEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        _categoryUiState.update { it.copy(isLoading = true) }
        getListMovies(categoryUiState.value.categorySelectedID)
        getGenreMovie()
        _categoryUiEvent.update { Event(CategoryUiEvent.ClickRetry) }
    }

    fun getListMovies(categorySelected: Int) {
        val result = getMoviesByGenreIDUseCase(genreId = categorySelected).map { pagingData ->
            pagingData.map {
                mediaUiMapper.map(it)
            }
        }
        _categoryUiState.update {
            it.copy(moviesResult = result, isLoading = false)
        }
    }

    private fun getGenreMovie() {
        viewModelScope.launch {
            val result = getGenreMovieUseCase().map {
                CategoryGenreUiState(id = it.id, name = it.name)
            }
            _categoryUiState.update {
                it.copy(categoryResult = result, isLoading = false)
            }
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.Error -> {
                _categoryUiState.update {
                    it.copy(
                        isLoading = false, error = listOf(
                            ErrorUiState(message = "not found", code = 404)
                        )
                    )
                }
            }

            LoadState.Loading -> _categoryUiState.update {
                it.copy(
                    isLoading = true,
                    error = emptyList()
                )
            }

            is LoadState.NotLoading -> {
                _categoryUiState.update { it.copy(isLoading = false, error = emptyList()) }
            }
        }

    }

    override fun onClickMedia(mediaId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickCategory(categoryId: Int) {
        _categoryUiState.update { it.copy(categorySelectedID = categoryId) }
        _categoryUiEvent.update { Event(CategoryUiEvent.ClickCategoryEvent(categoryId)) }
    }

}