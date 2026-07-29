package com.elhady.movies.core.network.dto.movie


import com.google.gson.annotations.SerializedName

data class ReviewsRemoteDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ResultDto>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)
