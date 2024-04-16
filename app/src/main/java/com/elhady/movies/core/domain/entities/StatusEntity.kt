package com.elhady.movies.core.domain.entities

data class StatusEntity(
    val success: Boolean,
    val statusCode: Int,
    val statusMessage: String
)
