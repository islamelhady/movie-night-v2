package com.elhady.movies.ui.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetReviewsUseCase
import com.elhady.movies.ui.base.BaseInteractionListener
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.ReviewUiMapper
import com.elhady.movies.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel<ReviewsUiState, ReviewInteraction>(ReviewsUiState()), BaseInteractionListener {

    val args = ReviewsFragmentArgs.fromSavedStateHandle(state)

    init {
        getData()
    }

    override fun getData() {
        _state.update { it.copy(isLoading = true, onErrors = emptyList()) }
        viewModelScope.launch {
            try {
                val result = getReviewsUseCase(mediaId = args.mediaId, type = args.mediaType).map {
                    reviewUiMapper.map(it)
                }
                _state.update {
                    it.copy(review = result, isLoading = false)
                }
            } catch (e: Exception) {

            }
        }
    }


}