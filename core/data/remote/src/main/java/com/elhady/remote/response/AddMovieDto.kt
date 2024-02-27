package com.elhady.remote.response

import com.google.gson.annotations.SerializedName

data class AddMovieDto(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?
)