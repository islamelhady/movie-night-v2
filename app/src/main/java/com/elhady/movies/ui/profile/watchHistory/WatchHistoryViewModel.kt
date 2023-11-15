package com.elhady.movies.ui.profile.watchHistory

import androidx.lifecycle.ViewModel
import com.elhady.movies.domain.usecases.GetWatchHistoryUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val getWatchHistoryUseCase: GetWatchHistoryUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(WatchHistoryUiState())
    val uiState = _uiState.asStateFlow()

    override fun getData() {
        getWatchHistoryUseCase()
    }


}