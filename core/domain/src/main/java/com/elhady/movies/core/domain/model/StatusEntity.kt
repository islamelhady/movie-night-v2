package com.elhady.movies.core.domain.model

data class StatusEntity(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)
