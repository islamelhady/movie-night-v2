package com.elhady.viewmodel.profile.watchHistory

import android.provider.SyncStateContract
import androidx.lifecycle.viewModelScope
import com.elhady.base.BaseViewModel
import com.elhady.viewmodel.mappers.WatchHistoryUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val getWatchHistoryUseCase: GetWatchHistoryUseCase,
    private val watchHistoryUiMapper: WatchHistoryUiMapper
) : BaseViewModel<WatchHistoryUiState, WatchHistoryUiEvent>(WatchHistoryUiState()),
    WatchHistoryInteractionListener {

    init {
        getData()
    }

    override fun getData() {
        viewModelScope.launch {
            getWatchHistoryUseCase().collect { list ->
                val result = list.map {
                    watchHistoryUiMapper.map(it)
                }
                _state.update {
                    it.copy(allMedia = result)
                }
            }
        }
    }

    override fun onClickMedia(item: MediaHistoryUiState) {
        if (item.mediaType.equals(Constants.MOVIE, true)) {
            sendEvent(WatchHistoryUiEvent.MovieEvent(item.id))
        } else {
            sendEvent(WatchHistoryUiEvent.SeriesEvent(item.id))
        }
    }

}
