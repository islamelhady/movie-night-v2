package com.elhady.movies.core.domain.model.mylist



data class FavoriteBodyRequestEntity(
    val isFavorite: Boolean?,
    val mediaId: Int?,
    val mediaType: String,
)
