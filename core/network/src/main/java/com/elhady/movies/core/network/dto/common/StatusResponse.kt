package com.elhady.movies.core.network.dto.common


import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?
)
