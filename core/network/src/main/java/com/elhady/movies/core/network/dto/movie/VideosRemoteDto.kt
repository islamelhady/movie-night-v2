package com.elhady.movies.core.network.dto.movie

import com.google.gson.annotations.SerializedName

data class VideosRemoteDto(
    @SerializedName("results")
    val results: List<MovieVideoRemoteDto>?
)
