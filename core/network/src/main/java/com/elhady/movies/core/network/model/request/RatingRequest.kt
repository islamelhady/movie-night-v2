package com.elhady.movies.core.network.model.request

import com.google.gson.annotations.SerializedName

data class RatingRequest(
    @SerializedName("value")
    val value: Float
)
