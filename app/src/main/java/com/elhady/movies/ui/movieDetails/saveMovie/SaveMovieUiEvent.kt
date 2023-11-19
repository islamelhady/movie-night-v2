package com.elhady.movies.ui.movieDetails.saveMovie
sealed interface SaveMovieUiEvent {
    data class DisplayMessage(val message: String) : SaveMovieUiEvent
}