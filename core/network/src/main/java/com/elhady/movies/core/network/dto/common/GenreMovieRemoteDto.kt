package com.elhady.movies.core.network.dto.common


import com.google.gson.annotations.SerializedName

data class GenreMovieRemoteDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
