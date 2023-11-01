package com.elhady.movies.ui.tvShowDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.tvShowDetails.GetTVShowDetailsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.tvShowDetails.tvShowUiMapper.TvShowDetailsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getTVShowDetailsUseCase: GetTVShowDetailsUseCase,
    private val tvShowDetailsUiMapper: TvShowDetailsUiMapper
) : BaseViewModel() {

    private val args = TvShowDetailsFragmentArgs.fromSavedStateHandle(state)

    private val _tvShowUiState = MutableStateFlow(TVShowDetailsUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        getTVShowDetails(args.tvShowId)
    }


    private fun getTVShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            val result = tvShowDetailsUiMapper.map(getTVShowDetailsUseCase(tvShowId))
            _tvShowUiState.update {
                it.copy(
                    tvShowDetailsResult = result
                )
            }
        }
    }
}