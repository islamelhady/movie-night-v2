package com.elhady.movies.ui.seriesDetails

import androidx.lifecycle.ViewModel
import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.ReviewUiState
import com.elhady.movies.ui.seriesDetails.seriesUiState.SeasonUiState

sealed class SeriesItems(val priority: Int){
    data class Header(val data: SeriesDetailsResultUiState): SeriesItems(0)
    data class Cast(val data: List<ActorUiState>): SeriesItems(1)
    data class Similar(val data: List<MediaUiState>): SeriesItems(2)
    data class Season(val data: List<SeasonUiState>): SeriesItems(3)
    data class Rating(val viewModel: ViewModel): SeriesItems(4)
    object ReviewText: SeriesItems(5)
    data class Review(val data: ReviewUiState): SeriesItems(6)
    object SeeAllReviews: SeriesItems(7)
}
