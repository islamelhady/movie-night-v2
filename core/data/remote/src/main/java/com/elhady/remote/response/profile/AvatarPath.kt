package com.elhady.remote.response.profile


import com.google.gson.annotations.SerializedName

data class AvatarPath(
    @SerializedName("avatar_path")
    val avatarPath: String?
)