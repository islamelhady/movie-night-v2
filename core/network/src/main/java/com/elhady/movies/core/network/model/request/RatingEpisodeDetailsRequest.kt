package com.elhady.movies.core.network.model.request

import com.google.gson.annotations.SerializedName

data class RatingEpisodeDetailsRequest(
    @SerializedName("value")
    val value: Float
)
