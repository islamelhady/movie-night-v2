package com.elhady.remote.response


import com.elhady.remote.ActorRemoteDto
import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<ActorRemoteDto>?,
    @SerializedName("id")
    val id: Int?
)