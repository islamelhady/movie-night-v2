package com.elhady.remote.request


import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("success")
    val success: Boolean?
)