package com.elhady.movies.feature.player.presentation.player

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.ErrorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PlayerUiState, PlayerUiEffect>(PlayerUiState()) {

    private val videoKey =
        savedStateHandle.get<String>("videoKey") ?: ""

    init {
        getData()
    }

    fun onEvent(event: PlayerUiEvent) {
        when (event) {
            PlayerUiEvent.BackClicked -> sendEffect(PlayerUiEffect.NavigateBack)
        }
    }

    private fun getData() {
        if (videoKey.isEmpty()) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errors = ErrorUiState.Generic
                )
            }
        } else {
            _state.update {
                it.copy(videoKey = videoKey, isLoading = false, errors = null)
            }
        }
    }
}
