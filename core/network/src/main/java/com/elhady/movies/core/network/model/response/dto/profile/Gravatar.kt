package com.elhady.movies.core.network.model.response.dto.profile


import com.google.gson.annotations.SerializedName

data class Gravatar(
    @SerializedName("hash")
    val hash: String? = null
)
