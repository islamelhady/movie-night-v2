package com.elhady.movies.data.remote.response


import com.elhady.movies.data.remote.response.actor.PersonDto
import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<PersonDto>?,
    @SerializedName("id")
    val id: Int?
)