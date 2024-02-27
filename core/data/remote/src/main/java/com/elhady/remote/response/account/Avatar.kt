package com.elhady.remote.response.account


import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar")
    val gravatar: Gravatar?,
    @SerializedName("tmdb")
    val avatarPath: AvatarPath?
)