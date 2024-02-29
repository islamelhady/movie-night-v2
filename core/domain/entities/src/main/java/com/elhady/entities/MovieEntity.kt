package com.elhady.entities

data class MovieEntity(
    val mediaID: Int,
    val mediaName: String,
    val mediaType:String = "movie",
    val mediaGenreEntities: List<GenreEntity>,
    val mediaRate: Float,
    val mediaImage: String,
    val mediaYear: String,
)