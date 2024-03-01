package com.elhady.viewmodel.profile.ratings

data class RatedUiState(
    val id: Int,
    val title: String,
    val rating: Float,
    val posterPath: String,
    var mediaType: String,
    val releaseDate: String
)