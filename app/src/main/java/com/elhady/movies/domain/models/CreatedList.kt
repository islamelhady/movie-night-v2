package com.elhady.movies.domain.models


data class CreatedList(
    val id: Int,
    val itemCount: Int,
    val name: String,
    val listType: String,
    val posterPath: List<String>
)
