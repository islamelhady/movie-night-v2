package com.elhady.movies.core.data.remote.response.movieDetails


import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("success")
    val success: Boolean?
)