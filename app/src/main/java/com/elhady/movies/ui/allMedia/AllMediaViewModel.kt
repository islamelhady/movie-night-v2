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
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : BaseViewModel<AllMediaUiState, AllMediaUiEvent>(AllMediaUiState()), MediaInteractionListener {

    val args = AllMediaFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        getData()
    }

    override fun getData() {
        getAllMedia()
    }

    private fun getAllMedia() {
        viewModelScope.launch {
            val items =
                getAllMediaByTypeUseCase(type = args.type, actionId = args.id).map { pagingData ->
                    pagingData.map { mediaUiMapper.map(it) }
                }
            _state.update {
                it.copy(allMedia = items, isLoading = false, onErrors = emptyList())
            }
        }
    }


    fun setErrorUiState(combinedLoadStates: CombinedLoadStates) {
        when (combinedLoadStates.refresh) {
            is LoadState.NotLoading -> {
                _state.update {
                    it.copy(isLoading = false, onErrors = emptyList())
                }
            }

            is LoadState.Loading -> {
                _state.update {
                    it.copy(isLoading = true, onErrors = emptyList())
                }
            }

            is LoadState.Error -> {
                _state.update {
                    it.copy(isLoading = false, onErrors = listOf("No network"))
                }
            }
        }

    }

    override fun onClickMedia(mediaId: Int) {
        if (checkMediaTypeUseCase(args.type)) {
            sendEvent(AllMediaUiEvent.ClickSeriesEvent(mediaId))
        } else {
            sendEvent(AllMediaUiEvent.ClickMovieEvent(mediaId))
        }
    }


}