package com.elhady.remote.response.actor


import com.elhady.remote.response.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieCreditsDto(
    @SerializedName("cast")
    val cast: List<MovieDto>?,
    @SerializedName("id")
    val id: Int?
)