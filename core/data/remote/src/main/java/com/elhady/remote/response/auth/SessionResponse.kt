package com.elhady.remote.response.auth


import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("success")
    val isSuccess: Boolean?,
    @SerializedName("session_id")
    val sessionId: String?,
)