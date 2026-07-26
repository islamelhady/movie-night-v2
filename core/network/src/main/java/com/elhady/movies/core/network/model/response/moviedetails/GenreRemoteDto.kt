package com.elhady.movies.core.network.model.response.moviedetails

import com.google.gson.annotations.SerializedName

data class GenreRemoteDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
