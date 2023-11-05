package com.elhady.movies.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.paging.map
import com.elhady.movies.domain.usecases.GetMoviesByGenreIDUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoviesByGenreIDUseCase: GetMoviesByGenreIDUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel() {

    val args = CategoryFragmentArgs.fromSavedStateHandle(savedStateHandle)


    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState = _categoryUiState.asStateFlow()

    override fun getData() {
        val result = getMoviesByGenreIDUseCase(genreId = Constants.ADVENTURE_ID).map { pagingData ->
            pagingData.map {
                mediaUiMapper.map(it)
            }
        }
        _categoryUiState.update {
            it.copy(moviesResult = result)
        }
    }

}