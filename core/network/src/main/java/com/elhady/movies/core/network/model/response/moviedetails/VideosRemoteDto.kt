package com.elhady.movies.core.network.model.response.moviedetails

import com.google.gson.annotations.SerializedName

data class VideosRemoteDto(
    @SerializedName("results")
    val results: List<MovieVideoRemoteDto>?
)
