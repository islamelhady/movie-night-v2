package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class Gravatar(
    @SerializedName("hash")
    val hash: String? = null
)
