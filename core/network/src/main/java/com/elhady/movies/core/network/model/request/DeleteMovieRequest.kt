package com.elhady.movies.core.network.model.request


import com.google.gson.annotations.SerializedName

data class DeleteMovieRequest(
    @SerializedName("media_id")
    val mediaId: Int? = null
)
