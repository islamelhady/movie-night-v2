package com.elhady.movies.core.common.domain.entities

data class StatusEntity(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)
