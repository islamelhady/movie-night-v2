package com.elhady.movies.core.network.model.response.dto.profile


import com.elhady.remote.response.dto.profile.Tmdb
import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar? = null,
    @SerializedName("tmdb")
    val tmdb: Tmdb? = null
)
