package com.elhady.viewmodel.profile.ratings

import androidx.lifecycle.viewModelScope
import com.elhady.base.BaseViewModel
import com.elhady.usecase.GetListOfRatedUseCase
import com.elhady.usecase.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val getListOfRatedUseCase: GetListOfRatedUseCase,
    private val ratedUiStateMapper: RatedUiStateMapper
) : BaseViewModel<MyRateUiState, MyRatingUiEvent>(MyRateUiState()), MyRatingInteractionListener {


    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getListOfRatedUseCase().map {
                ratedUiStateMapper.map(it)
            }
            _state.update {
                it.copy(ratedList = result, isLoading = false)
            }

        }
    }

    override fun onClickRating(rated: RatedUiState) {
        if (rated.mediaType.equals(MediaType.MOVIES.value, true)) {
            sendEvent(MyRatingUiEvent.MovieEvent(rated.id))
        } else {
            sendEvent(MyRatingUiEvent.SeriesEvent(rated.id))
        }
    }

}
