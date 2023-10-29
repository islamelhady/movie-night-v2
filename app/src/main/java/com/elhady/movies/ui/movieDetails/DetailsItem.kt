package com.elhady.movies.ui.movieDetails

sealed class DetailsItem(val priority: Int){
    data class Header(val data: MovieDetailsUiState): DetailsItem(0)
}