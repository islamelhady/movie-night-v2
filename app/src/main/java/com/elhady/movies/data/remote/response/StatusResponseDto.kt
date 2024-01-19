package com.elhady.movies.data.remote.response

import com.google.gson.annotations.SerializedName

data class StatusResponseDto(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?
)