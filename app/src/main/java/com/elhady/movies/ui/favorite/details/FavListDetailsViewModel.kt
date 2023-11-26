package com.elhady.movies.ui.favorite.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.favList.GetFavListDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.movieDetails.ErrorUiState
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavListDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFavListDetailsUseCase: GetFavListDetailsUseCase,
    private val mediaUiStateMapper: MediaUiStateMapper
) : BaseViewModel<ListDetailsUIState, ListDetailsUiEvent>(ListDetailsUIState()),
    ListDetailsInteractionListener {

    val args = FavListDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getData()
    }

    override fun getData() {
        _state.update {
            it.copy(isLoading = true, isEmpty = false, error = emptyList())
        }
        viewModelScope.launch {
            try {
                val result =
                    getFavListDetailsUseCase(args.mediaId).map { mediaUiStateMapper.map(it) }
                _state.update {
                    it.copy(
                        isLoading = false,
                        isEmpty = result.isEmpty(),
                        savedMedia = result
                    )
                }

            } catch (t: Throwable) {
                _state.update {
                    it.copy(
                        isLoading = false, error = listOf(
                            ErrorUiState(t.message.toString(), 400)
                        )
                    )
                }
            }
        }
    }

    override fun onItemClick(item: FavMediaUiState) {
        sendEvent(ListDetailsUiEvent.OnItemSelected(item))
    }
}