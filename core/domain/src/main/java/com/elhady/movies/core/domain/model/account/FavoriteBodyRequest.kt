package com.elhady.movies.core.domain.model.account

import com.elhady.movies.core.common.MediaType

data class FavoriteBodyRequest(
    val mediaId: Int,
    val mediaType: MediaType,
    val isFavorite: Boolean
)
