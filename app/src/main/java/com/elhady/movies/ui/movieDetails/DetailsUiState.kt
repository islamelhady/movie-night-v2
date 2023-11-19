package com.elhady.movies.ui.movieDetails

import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.ReviewUiState


data class DetailsUiState(
    val detailsItemsResult: List<DetailsItem> = mutableListOf(),
    val movieDetailsResult: MovieDetailsUiState = MovieDetailsUiState(),
    val movieCastResult: List<ActorUiState> = emptyList(),
    val similarMoviesResult: List<MediaUiState> = emptyList(),
    val movieReviewsResult: List<ReviewUiState> = emptyList(),
    val ratingValue: Float = 0f,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isLogin: Boolean = false,
    val errorUIStates: List<ErrorUiState> = emptyList(),
)

data class ErrorUiState(
    val message: String = "",
    val code: Int = 0,
)