package com.elhady.movies.data.remote.response

data class MovieResponse(
    val page: Int?,
    val movies: List<Movie?>?,
    val total_pages: Int?,
    val total_results: Int?
)