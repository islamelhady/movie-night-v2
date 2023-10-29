package com.elhady.movies.data.remote.response.actor


import com.elhady.movies.data.remote.response.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieCreditsDto(
    @SerializedName("cast")
    val cast: List<MovieDto>?,
    @SerializedName("id")
    val id: Int?
)