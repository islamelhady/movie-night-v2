package com.elhady.movies.ui.movieDetails

import androidx.lifecycle.ViewModel
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.ReviewUiState


sealed class DetailsItem(val priority: Int){
    data class Header(val data: MovieDetailsUiState): DetailsItem(0)
    data class Rating(val viewModel: ViewModel): DetailsItem(1)
    data class Cast(val data: List<ActorUiState>): DetailsItem(2)
    data class Similar(val data: List<MediaUiState>): DetailsItem(3)
    object ReviewsText: DetailsItem(4)
    data class Reviews(val data: ReviewUiState): DetailsItem(5)
    object SeeAllReviewsButton: DetailsItem(6)
}