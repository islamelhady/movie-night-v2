package com.elhady.movies.core.network.dto.common


import com.google.gson.annotations.SerializedName

data class YoutubeVideoDetailsDto(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("site")
    val site: String? = null,
    @SerializedName("type")
    val type: String? = null,
)
