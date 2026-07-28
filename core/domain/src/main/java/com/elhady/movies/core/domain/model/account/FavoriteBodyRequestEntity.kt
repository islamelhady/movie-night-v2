package com.elhady.movies.core.domain.model.account

data class FavoriteBodyRequestEntity(
    val mediaId: Int,
    val mediaType: String,
    val isFavorite: Boolean
)
