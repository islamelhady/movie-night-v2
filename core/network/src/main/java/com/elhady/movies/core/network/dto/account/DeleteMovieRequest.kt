package com.elhady.movies.core.network.dto.account


import com.google.gson.annotations.SerializedName

data class DeleteMovieRequest(
    @SerializedName("media_id")
    val mediaId: Int? = null
)
