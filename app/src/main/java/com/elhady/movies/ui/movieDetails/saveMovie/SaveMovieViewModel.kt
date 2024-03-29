package com.elhady.movies.ui.movieDetails.saveMovie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.GetCreatedListUseCase
import com.elhady.movies.domain.usecases.favList.SaveMovieToFavListUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveMovieViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveMovieToFavListUseCase: SaveMovieToFavListUseCase,
    private val getCreatedListUseCase: GetCreatedListUseCase,
    private val favListItemUiStateMapper: FavListItemUiStateMapper,
) : BaseViewModel<FavListUiState, SaveMovieUiEvent>(FavListUiState()), SaveListInteractionListener {

    val args = SaveMovieBottomSheetArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getData()
    }


    override fun getData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = emptyList()) }
            try {
                _state.update {
                    it.copy(
                        isLoading = false,
                        myListItemUI = getCreatedListUseCase().map { favListItemUiStateMapper.map(it) }
                    )
                }
            } catch (error: Throwable) {
                _state.update {
                    it.copy(isLoading = false)
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
            sendEvent(SaveMovieUiEvent.DisplayMessage(message ?: ""))
        }
    }
}