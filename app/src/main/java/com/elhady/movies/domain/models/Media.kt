package com.elhady.movies.domain.models

data class Media(
    val mediaID: Int,
    val mediaName: String,
    val mediaType:String,
    val mediaRate: Float,
    val mediaImage: String,
    val mediaDate: String,
)