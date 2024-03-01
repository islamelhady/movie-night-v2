package com.elhady.ui.seriesDetails

import androidx.lifecycle.ViewModel
import com.elhady.viewmodel.models.ActorUiState
import com.elhady.viewmodel.models.MediaUiState
import com.elhady.viewmodel.models.ReviewUiState
import com.elhady.viewmodel.seriesDetails.SeasonUiState
import com.elhady.viewmodel.seriesDetails.SeriesDetailsResultUiState

sealed class SeriesItems(val priority: Int){
    data class Header(val data: SeriesDetailsResultUiState): SeriesItems(0)
    data class Rating(val viewModel: ViewModel): SeriesItems(1)
    data class Cast(val data: List<ActorUiState>): SeriesItems(2)
    data class Similar(val data: List<MediaUiState>): SeriesItems(3)
    data class Season(val data: List<SeasonUiState>): SeriesItems(4)
    object ReviewText: SeriesItems(5)
    data class Review(val data: ReviewUiState): SeriesItems(6)
    object SeeAllReviews: SeriesItems(7)
}
