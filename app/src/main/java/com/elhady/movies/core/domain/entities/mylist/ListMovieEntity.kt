package com.elhady.movies.core.common.domain.entities.mylist

data class ListMovieEntity(
    val mediaId: Int,
    val listId: Int,
    val mediaType: String,
    val nameList: String,
    val posterPath: String,
)
