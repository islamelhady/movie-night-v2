package com.elhady.movies.core.domain.model.common

data class AccountStates(
    val favorite: Boolean = false,
    val watchlist: Boolean = false,
    val id: Int = 0,
    val rating: Double = 0.0
)
