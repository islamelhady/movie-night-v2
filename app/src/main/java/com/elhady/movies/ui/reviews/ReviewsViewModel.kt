package com.elhady.movies.ui.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.elhady.movies.domain.usecases.GetReviewsUseCase
import com.elhady.movies.ui.base.BaseViewModel
import com.elhady.movies.ui.mappers.ReviewUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    state: SavedStateHandle
    private val getReviewsUseCase: GetReviewsUseCase,
    private val reviewUiMapper: ReviewUiMapper
) : BaseViewModel() {

    val args = ReviewsFragmentArgs.fromSavedStateHandle(state)
    private val _reviewUiState = MutableStateFlow(ReviewsUiState())
    val reviewUiState = _reviewUiState.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        viewModelScope.launch {
            try {
                val result = getReviewsUseCase(args.mediaId).map {
                    reviewUiMapper.map(it)
                }
                _reviewUiState.update {
                    it.copy(review = result)
                }
            } catch (e: Exception) {

            }
        }
    }


}