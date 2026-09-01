package com.elhady.movies.core.domain.model.movie

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.domain.model.common.Genre

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<Genre>,
    val rate: Double,
    val year: String = "",
    val mediaType: MediaType = MediaType.MOVIE,
)
