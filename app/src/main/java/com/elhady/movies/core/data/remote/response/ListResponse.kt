package com.elhady.movies.core.data.remote.response


import com.google.gson.annotations.SerializedName

data class ListResponse(
    @SerializedName("list_id")
    val listId: Int? = null,
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
    @SerializedName("success")
    val success: Boolean? = null
)