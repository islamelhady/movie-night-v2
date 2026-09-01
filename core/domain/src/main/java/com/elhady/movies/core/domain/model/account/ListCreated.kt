package com.elhady.movies.core.domain.model.account

import com.elhady.movies.core.common.MediaType

data class ListCreated(
    val id: Int? = null,
    val itemCount: Int? = null,
    val listType: MediaType = MediaType.MOVIE,
    val name: String? = null,
    val posterPath: List<String>? = null,
)
