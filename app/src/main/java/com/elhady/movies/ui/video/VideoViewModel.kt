package com.elhady.movies.ui.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetTrailerUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTrailerUseCase: GetTrailerUseCase
) : BaseViewModel<TrailerUiState>(TrailerUiState()) {

    val args = VideoFragmentArgs.fromSavedStateHandle(savedStateHandle)
    
    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getTrailerUseCase(args.mediaType, args.mediaId)
            _state.update { it.copy(videoKey = result.videoKey, isLoading = false) }
        }
    }
}