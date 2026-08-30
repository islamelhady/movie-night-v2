package com.elhady.movies.feature.watchlist.presentation.listcontents

import com.elhady.movies.core.common.MediaType

data class MovieUiState(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val genres: String,
    val year: String,
    val rating: Double,
    val mediaType: MediaType,
){
    val rate: Double
        get() = (rating * 10.0).toInt() / 10.0
}
