package com.elhady.movies.feature.details.presentation.moviedetails

import com.elhady.movies.core.ui.state.UserListUiState

sealed interface MovieDetailsUiEffect {
    object NavigateBack : MovieDetailsUiEffect
    data class NavigateToPeopleDetails(val personId: Int) : MovieDetailsUiEffect
    data class NavigateToMovieDetails(val movieId: Int) : MovieDetailsUiEffect
    data class ShowSnackBar(val message: String) : MovieDetailsUiEffect
    object ShowRateBottomSheet : MovieDetailsUiEffect
    data class ShowSaveToListBottomSheet(val lists: List<UserListUiState>) : MovieDetailsUiEffect
    data class NavigateToShowMore(val movieId: Int) : MovieDetailsUiEffect
    
    // Bottom Sheet interactions
    object CloseBottomSheet : MovieDetailsUiEffect
    object AddListToBottomSheet : MovieDetailsUiEffect
    object DoneEvent : MovieDetailsUiEffect
}
