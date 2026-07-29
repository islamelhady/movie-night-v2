package com.elhady.movies.core.network.dto.movie

import com.google.gson.annotations.SerializedName

data class RatingRequest(
    @SerializedName("value")
    val value: Float
)
