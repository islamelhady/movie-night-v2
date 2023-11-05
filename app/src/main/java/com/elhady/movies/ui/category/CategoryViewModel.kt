package com.elhady.movies.ui.category

import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : BaseViewModel() {
    // TODO: Implement the ViewModel

    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState = _categoryUiState.asStateFlow()

    override fun getData() {
        TODO("Not yet implemented")
    }
}