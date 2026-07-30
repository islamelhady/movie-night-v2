package com.elhady.movies.core.ui.state

import kotlin.math.roundToInt

data class MediaVerticalUiState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 10.0).roundToInt() / 10.0
}
