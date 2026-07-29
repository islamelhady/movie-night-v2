package com.elhady.movies.core.domain.model.account

data class ListCreatedEntity(
    val id: Int? = null,
    val itemCount: Int? = null,
    val listType: String? = null,
    val name: String? = null,
    val posterPath: List<String>? = null,
)
