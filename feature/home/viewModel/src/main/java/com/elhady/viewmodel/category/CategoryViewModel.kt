package com.elhady.viewmodel.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.base.BaseViewModel
import com.elhady.viewmodel.mappers.MediaUiMapper
import com.elhady.usecase.GetCategoryByGenreUseCase
import com.elhady.usecase.GetGenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCategoryByGenreIDUseCase: GetCategoryByGenreUseCase,
    private val getGenreListUseCase: GetGenreListUseCase,
    private val mediaUiMapper: MediaUiMapper,
    private val genreUiMapper: GenreUiMapper
) : BaseViewModel<CategoryUiState, CategoryUiEvent>(CategoryUiState()), MediaInteractionListener,
    CategoryInteractionListener {

    val args = CategoryFragmentArgs.fromSavedStateHandle(savedStateHandle)


    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        getMediaList(_state.value.categorySelectedID)
        getCategoryGenreList()
        sendEvent(CategoryUiEvent.ClickRetry)
    }

    fun getMediaList(categorySelected: Int) {
        viewModelScope.launch {
            val result = getCategoryByGenreIDUseCase(
                type = args.mediaType,
                genreId = categorySelected
            ).map { pagingData ->
                pagingData.map {
                    mediaUiMapper.map(it)
                }
            }
            _state.update {
                it.copy(moviesResult = result, isLoading = false)
            }
        }
    }

    private fun getCategoryGenreList() {
        viewModelScope.launch {
            val result = getGenreListUseCase(args.mediaType).map {
                genreUiMapper.map(it)
            }
            _state.update {
                it.copy(categoryResult = result, isLoading = false)
            }
        }
    }

    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false)
                }
            }

            LoadState.Loading -> _state.update {
                it.copy(
                    isLoading = true,
                    onErrors = emptyList()
                )
            }

            is LoadState.NotLoading -> {
                _state.update { it.copy(isLoading = false, onErrors = emptyList()) }
            }
        }

    }

    override fun onClickMedia(mediaId: Int) {
        sendEvent(CategoryUiEvent.ClickMediaEvent(mediaId))
    }

    override fun onClickCategory(categoryId: Int) {
        _state.update { it.copy(categorySelectedID = categoryId) }
        sendEvent(CategoryUiEvent.ClickCategoryEvent(categoryId))
    }

}