package com.elhady.movies.ui.movieDetails

import com.elhady.movies.ui.models.ActorUiState
import com.elhady.movies.ui.models.MediaUiState
import com.elhady.movies.ui.models.ReviewUiState


data class DetailsUiState(
    val detailsItemsResult: List<DetailsItem> = emptyList(),
    val movieDetailsResult: MovieDetailsUiState = MovieDetailsUiState(),
    val movieCastResult: List<ActorUiState> = emptyList(),
    val similarMoviesResult: List<MediaUiState> = emptyList(),
    val movieReviewsResult: List<ReviewUiState> = emptyList(),
    val ratingValue: Float = 0f,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isLogin: Boolean = false,
    val onErrors: List<String> = emptyList(),
)

data class MovieDetailsUiState(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val overview: String = "",
    val specialNumber: Int = 0,
    val minutes: Int = 0,
    val hours: Int = 0,
    val voteAverage: String = "",
    val genres: String = "",
    val review: Int = 0,
    val releaseDate: String = ""
)