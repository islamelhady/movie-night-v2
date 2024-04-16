package com.elhady.movies.core.data.remote.response.dto.profile


import com.elhady.remote.response.dto.profile.Tmdb
import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar? = null,
    @SerializedName("tmdb")
    val tmdb: Tmdb? = null
)