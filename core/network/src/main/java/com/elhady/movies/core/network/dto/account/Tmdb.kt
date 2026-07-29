package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class Tmdb(
    @SerializedName("avatar_path")
    val avatarPath: String? = null
)
