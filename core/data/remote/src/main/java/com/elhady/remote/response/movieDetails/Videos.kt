package com.elhady.remote.response.movieDetails

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val results: List<MovieVideo>?
)