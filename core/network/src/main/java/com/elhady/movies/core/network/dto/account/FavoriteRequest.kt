package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class FavoriteRequest(
    @SerializedName("favorite")
    val favorite: Boolean? = null,
    @SerializedName("media_id")
    val mediaId: Int? = null,
    @SerializedName("media_type")
    val mediaType: String? = null
)
