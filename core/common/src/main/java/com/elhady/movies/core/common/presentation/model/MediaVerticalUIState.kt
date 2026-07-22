package com.elhady.movies.core.common.presentation.model

import kotlin.math.roundToInt

data class MediaVerticalUIState(
    val id: Int,
    val imageUrl: String,
    val rate: Double
) {
    fun formattedRate(): Double = (rate * 10.0).roundToInt() / 10.0
}
