package com.elhady.viewmodel.movieDetails.saveMovie
sealed interface SaveMovieUiEvent {
    data class DisplayMessage(val message: String) : SaveMovieUiEvent
}