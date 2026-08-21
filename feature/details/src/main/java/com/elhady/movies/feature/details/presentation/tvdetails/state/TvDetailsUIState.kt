package com.elhady.movies.feature.details.presentation.tvdetails.state

data class TvDetailsUIState(
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
)



