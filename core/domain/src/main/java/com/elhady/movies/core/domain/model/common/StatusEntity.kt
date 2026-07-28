package com.elhady.movies.core.domain.model.common

data class StatusEntity(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)
