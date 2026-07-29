package com.elhady.movies.core.network.dto.movie

import com.google.gson.annotations.SerializedName

data class RecommendationsRemoteDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val recommendedMovies: List<RecommendedMovieDto>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)
