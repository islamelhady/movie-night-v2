package com.elhady.movies.core.data.remote.response.moviedetails

import com.google.gson.annotations.SerializedName

data class VideosRemoteDto(
    @SerializedName("results")
    val results: List<MovieVideoRemoteDto>?
)