package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    state: SavedStateHandle
) : BaseViewModel() {


    val args = MovieDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _detailsUiState = MutableStateFlow(DetailsUiState())
    val detailsUiState = _detailsUiState.asStateFlow()
    override fun getData() {
        TODO("Not yet implemented")
    }


}