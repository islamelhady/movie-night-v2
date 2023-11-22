package com.elhady.movies.ui.profile.watchHistory

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetWatchHistoryUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.WatchHistoryUiMapper
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            Event(WatchHistoryUiEvent.MovieEvent(item.id))
        } else {
            Event(WatchHistoryUiEvent.SeriesEvent(item.id))
        }
    }

}
