package com.elhady.movies.ui.movieDetails.saveMovie

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.GetCreatedListUseCase
import com.elhady.movies.domain.usecases.favList.SaveMovieToFavListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.movieDetails.ErrorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveMovieViewModel @Inject constructor(
    private val saveMovieToFavListUseCase: SaveMovieToFavListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase,
    private val favListItemUiStateMapper: FavListItemUiStateMapper
) : BaseViewModel() {

    private val _saveUiState = MutableStateFlow(FavListUiState())
    val saveUiState = _saveUiState.asStateFlow()
    override fun getData() {
        viewModelScope.launch {
            _saveUiState.update { it.copy(isLoading = true, error = emptyList()) }
            try {
                _saveUiState.update {
                    it.copy(
                        isLoading = false,
                        myListItemUI = getCreatedListUseCase().map { favListItemUiStateMapper.map(it) }
                    )
                }
            } catch (error: Throwable) {
                _saveUiState.update {
                    it.copy(
                        isLoading = false,
                        error = listOf(ErrorUiState(error.message.toString(), 404))
                    )
                }
            }
        }
    }
}