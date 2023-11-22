package com.elhady.movies.ui.movieDetails.saveMovie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.GetCreatedListUseCase
import com.elhady.movies.domain.usecases.favList.SaveMovieToFavListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.movieDetails.ErrorUiState
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveMovieViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveMovieToFavListUseCase: SaveMovieToFavListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase,
    private val favListItemUiStateMapper: FavListItemUiStateMapper,
) : BaseViewModel<FavListUiState>(FavListUiState()), SaveListInteractionListener {

    val args = SaveMovieBottomSheetArgs.fromSavedStateHandle(savedStateHandle)

    private val _saveUiState = MutableStateFlow(FavListUiState())
    val saveUiState = _saveUiState.asStateFlow()

    private val _saveUiEvent = MutableStateFlow<Event<SaveMovieUiEvent>?>(null)
    val saveUiEvent = _saveUiEvent.asStateFlow()

    init {
        getData()
    }


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

    override fun onClickSaveList(listId: Int) {
        viewModelScope.launch {
            val message = try {
                saveMovieToFavListUseCase(listId, args.movieId)
            } catch (t: Throwable) {
                t.message.toString()
            }
            _saveUiEvent.update { Event(SaveMovieUiEvent.DisplayMessage(message ?: "")) }
        }
    }
}