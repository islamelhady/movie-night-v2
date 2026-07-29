package com.elhady.movies.core.network.dto.tvshow

import com.google.gson.annotations.SerializedName

data class RateRequest(
    @SerializedName("value")
    val value:Double
)
