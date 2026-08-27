package com.elhady.movies.feature.details.presentation.tvdetails

sealed interface TvDetailsUiEvent {
    object BackClicked: TvDetailsUiEvent
    object PlayClicked: TvDetailsUiEvent
    object RateClicked: TvDetailsUiEvent
    object SaveClicked: TvDetailsUiEvent
    object FavouriteClicked: TvDetailsUiEvent
    object WatchlistClicked: TvDetailsUiEvent
    object ShowMoreCastClicked: TvDetailsUiEvent
    object ShowMoreRecommendedClicked: TvDetailsUiEvent
    data class PersonClicked(val personId: Int) : TvDetailsUiEvent
    data class SeasonClicked(val seasonNumber: Int) : TvDetailsUiEvent
    data class RecommendationClicked(val tvShowId: Int) : TvDetailsUiEvent
    data class RatingChanged(val rating: Float) : TvDetailsUiEvent
    object RatingSubmitted : TvDetailsUiEvent
    data class ListSelected(val listId: Int) : TvDetailsUiEvent
    object DoneAddingLists : TvDetailsUiEvent
    object AddNewListClicked : TvDetailsUiEvent
    data class CreateNewListClicked(val listName: String) : TvDetailsUiEvent
    object Retry : TvDetailsUiEvent
    object DismissPlayerClicked : TvDetailsUiEvent
}
