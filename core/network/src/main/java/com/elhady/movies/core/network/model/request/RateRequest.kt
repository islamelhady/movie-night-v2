package com.elhady.movies.core.network.model.request

import com.google.gson.annotations.SerializedName

data class RateRequest(
    @SerializedName("value")
    val value:Double
)
