package com.elhady.movies.core.data.remote.response.movieDetails

import com.google.gson.annotations.SerializedName

data class CreditsRemoteDto(
    @SerializedName("cast")
    val cast: List<CastRemoteDto>?,
    @SerializedName("crew")
    val crew: List<CrewRemoteDto>?
)