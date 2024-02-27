package com.elhady.remote.response


import com.elhady.remote.PersonDto
import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<PersonDto>?,
    @SerializedName("id")
    val id: Int?
)