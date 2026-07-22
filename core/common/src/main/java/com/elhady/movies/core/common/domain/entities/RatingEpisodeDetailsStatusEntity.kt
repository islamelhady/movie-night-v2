package com.elhady.movies.core.common.domain.entities

data class RatingEpisodeDetailsStatusEntity(
    val statusCode: Int = 0,
    val statusMessage: String = "",
    val success: Boolean = false
)
