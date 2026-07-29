package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class GravatarDto(
    @SerializedName("hash")
    val hash: String? = null
)
