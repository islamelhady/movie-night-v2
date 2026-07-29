package com.elhady.movies.core.network.dto.movie

import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<CastDto>?,
    @SerializedName("crew")
    val crew: List<CrewDto>?
)
