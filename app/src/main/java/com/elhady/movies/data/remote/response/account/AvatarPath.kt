package com.elhady.movies.data.remote.response.account


import com.google.gson.annotations.SerializedName

data class AvatarPath(
    @SerializedName("avatar_path")
    val avatarPath: String?
)