package com.elhady.movies.data.remote.response.video


import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<ResultDto?>?
)