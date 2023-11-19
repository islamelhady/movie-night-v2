package com.elhady.movies.ui.movieDetails.saveMovie

import com.elhady.movies.domain.usecases.favList.SaveMovieToFavListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SaveMovieViewModel @Inject constructor(private val saveMovieToFavListUseCase: SaveMovieToFavListUseCase) : BaseViewModel() {

    private val _saveUiState = MutableStateFlow(FavListUiState())
    val saveUiState = _saveUiState.asStateFlow()
    override fun getData() {

    }
}