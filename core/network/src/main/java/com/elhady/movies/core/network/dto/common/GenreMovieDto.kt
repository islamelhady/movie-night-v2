package com.elhady.movies.core.network.dto.common


import com.google.gson.annotations.SerializedName

data class GenreMovieDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
