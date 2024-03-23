package com.elhady.movies.core.data.remote.response.movieDetails

import com.google.gson.annotations.SerializedName

data class VideosRemoteDto(
    @SerializedName("results")
    val results: List<MovieVideoRemoteDto>?
)