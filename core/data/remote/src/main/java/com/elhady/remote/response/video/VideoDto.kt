package com.elhady.remote.response.video


import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<ResultDto?>?
)