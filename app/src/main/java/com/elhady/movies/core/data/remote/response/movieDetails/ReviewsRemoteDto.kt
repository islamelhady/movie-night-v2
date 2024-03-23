package com.elhady.movies.core.data.remote.response.movieDetails


import com.google.gson.annotations.SerializedName

data class ReviewsRemoteDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)