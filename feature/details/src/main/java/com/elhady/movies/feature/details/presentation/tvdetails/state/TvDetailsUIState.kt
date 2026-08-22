package com.elhady.movies.feature.details.presentation.tvdetails.state

import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsItem

data class TvDetailsUIState(
    val tvDetailsItems: List<TvDetailsItem> = emptyList(),
    val infoUIState: InfoUIState = InfoUIState.Loading,
    val castUIState: CastUIState = CastUIState.Loading,
    val seasonsUIState: SeasonsUIState = SeasonsUIState.Loading,
    val reviewsUIState: ReviewsUIState = ReviewsUIState.Loading,
    val recommendationsUIState: RecommendationsUIState =
        RecommendationsUIState.Loading,
    val trailerUIState: TrailerUIState = TrailerUIState.Loading,
    val ratingUIState: RatingUIState = RatingUIState(),
    val userListsUIState: UserListsUIState = UserListsUIState.Idle,
    val userSelectedLists: List<Int> = emptyList(),
    val isLogin: Boolean = false,
    val isLoading: Boolean = false,
    val error: List<String>? = null
) {
    val isFailure: Boolean get() = error != null ||
            infoUIState is InfoUIState.Error ||
            castUIState is CastUIState.Error ||
            seasonsUIState is SeasonsUIState.Error ||
            reviewsUIState is ReviewsUIState.Error ||
            recommendationsUIState is RecommendationsUIState.Error
}



