package com.elhady.movies.ui.profile.watchHistory

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetWatchHistoryUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val getWatchHistoryUseCase: GetWatchHistoryUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(WatchHistoryUiState())
    val uiState = _uiState.asStateFlow()

    override fun getData() {
        viewModelScope.launch {
            val result = getWatchHistoryUseCase().collect{ list ->
                list.map {

                }

            }

           _uiState.update {
                it.copy(allMedia = result)
            }
        }


    }


}