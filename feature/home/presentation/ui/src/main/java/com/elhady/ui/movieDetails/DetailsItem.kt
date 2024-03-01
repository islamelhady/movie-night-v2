package com.elhady.ui.movieDetails

import androidx.lifecycle.ViewModel
import com.elhady.viewmodel.models.ActorUiState
import com.elhady.viewmodel.models.MediaUiState
import com.elhady.viewmodel.models.ReviewUiState
import com.elhady.viewmodel.movieDetails.MovieDetailsUiState


sealed class DetailsItem(val priority: Int){
    data class Header(val data: MovieDetailsUiState): DetailsItem(0)
    data class Rating(val viewModel: ViewModel): DetailsItem(1)
    data class Cast(val data: List<ActorUiState>): DetailsItem(2)
    data class Similar(val data: List<MediaUiState>): DetailsItem(3)
    object ReviewsText: DetailsItem(4)
    data class Reviews(val data: ReviewUiState): DetailsItem(5)
    object SeeAllReviewsButton: DetailsItem(6)
}