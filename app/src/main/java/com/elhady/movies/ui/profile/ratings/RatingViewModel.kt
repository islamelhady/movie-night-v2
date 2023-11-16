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
class RatingViewModel @Inject constructor(private val getListOfRatedUseCase: GetListOfRatedUseCase) : BaseViewModel() {

    private val _rateUiState = MutableStateFlow(MyRateUiState())
    val rateUiState = _rateUiState.asStateFlow()

    override fun getData() {
        viewModelScope.launch {
            val result = getListOfRatedUseCase()
            _rateUiState.update {
                it.copy(ratedList = result)
            }

        }
    }
}