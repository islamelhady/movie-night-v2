package com.elhady.movies.ui.movieDetails

import com.elhady.movies.ui.models.ReviewUiState


data class DetailsUiState(
    val movieDetailsResult: DetailsItem = DetailsItem.Header(MovieDetailsUiState()),
    val movieCastResult: DetailsItem = DetailsItem.Cast(emptyList()),
    val similarMoviesResult: DetailsItem = DetailsItem.Similar(emptyList()),
    val movieReviewsResult: DetailsItem = DetailsItem.Reviews(emptyList()),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorUIStates: List<Error> = emptyList(),
)

data class ErrorUiState(
    val message: String = "",
    val code: Int = 0,
)