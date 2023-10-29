package com.elhady.movies.ui.movieDetails

sealed interface DetailsItem{
    data class Header(val data: MovieDetailsUiState): DetailsItem
}