package com.elhady.movies.domain.models

data class Review(
    val userName: String,
    val userImage: String,
    val name: String,
    val content: String,
    val rating: Float,
    val createDate: String
)
