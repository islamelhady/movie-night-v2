package com.elhady.movies.core.domain.model.account

data class FavoriteBodyRequest(
    val mediaId: Int,
    val mediaType: String,
    val isFavorite: Boolean
)
