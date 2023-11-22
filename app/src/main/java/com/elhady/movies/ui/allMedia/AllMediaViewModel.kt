package com.elhady.movies.ui.allMedia

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import com.elhady.movies.domain.usecases.seeAllMedia.CheckMediaTypeUseCase
import com.elhady.movies.domain.usecases.seeAllMedia.GetAllMediaByTypeUseCase
import com.elhady.movies.ui.adapter.MediaInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.MediaUiMapper
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMediaViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val getAllMediaByTypeUseCase: GetAllMediaByTypeUseCase,
    private val checkMediaTypeUseCase: CheckMediaTypeUseCase,
    private val mediaUiMapper: MediaUiMapper
) : BaseViewModel<AllMediaUiState>(AllMediaUiState()), MediaInteractionListener {

    private val args =  AllMediaFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiEvent = MutableStateFlow<Event<AllMediaUiEvent>?>(null)
    val uiEvent = _uiEvent.asStateFlow()

    init {
        getData()
    }
    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        getAllMedia()
    }


    private fun getAllMedia() {
        viewModelScope.launch {
            val items = getAllMediaByTypeUseCase(type = args.type, actionId = args.id).map { pagingData ->
                pagingData.map { mediaUiMapper.map(it) }
            }
            _state.update {
                it.copy(allMedia = items, isLoading = false)
            }
        }
    }


    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, error = emptyList())
                }
            }

            is LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, error = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, error = listOf(Error(404, "Not found.")))
                }
            }
        }

    }

    override fun onClickMedia(mediaId: Int) {
        if(checkMediaTypeUseCase(args.type)){
            _uiEvent.update {
                Event(AllMediaUiEvent.ClickSeriesEvent(mediaId))
            }
        } else {
            _uiEvent.update {
                Event(AllMediaUiEvent.ClickMovieEvent(mediaId))
            }
        }
    }


}