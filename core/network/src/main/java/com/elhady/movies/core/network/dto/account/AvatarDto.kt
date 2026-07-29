package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class AvatarDto(
    @SerializedName("gravatar")
    val gravatar: Gravatar? = null,
    @SerializedName("tmdb")
    val tmdb: TmdbDto? = null
)
