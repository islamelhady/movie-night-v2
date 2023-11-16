package com.elhady.movies.ui.profile.ratings

import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetListOfRatedUseCase
import com.elhady.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val getListOfRatedUseCase: GetListOfRatedUseCase,
    private val ratedUiStateMapper: RatedUiStateMapper
) : BaseViewModel(), MyRatingInteractionListener {

    private val _rateUiState = MutableStateFlow(MyRateUiState())
    val rateUiState = _rateUiState.asStateFlow()

    init {
        getData()
    }
    override fun getData() {
        _rateUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getListOfRatedUseCase().map {
                ratedUiStateMapper.map(it)
            }
            _rateUiState.update {
                it.copy(ratedList = result, isLoading = false)
            }

        }
    }

    override fun onClickRating() {
        TODO("Not yet implemented")
    }
}