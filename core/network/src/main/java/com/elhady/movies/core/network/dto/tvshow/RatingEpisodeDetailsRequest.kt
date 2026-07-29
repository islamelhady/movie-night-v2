package com.elhady.movies.core.network.dto.tvshow

import com.google.gson.annotations.SerializedName

data class RatingEpisodeDetailsRequest(
    @SerializedName("value")
    val value: Float
)
