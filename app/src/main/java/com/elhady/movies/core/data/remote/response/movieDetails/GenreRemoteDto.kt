package com.elhady.movies.core.data.remote.response.movieDetails

import com.google.gson.annotations.SerializedName

data class GenreRemoteDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)