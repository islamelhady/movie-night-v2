package com.elhady.movies.core.domain.entities.tvdetails

import com.elhady.movies.core.domain.entities.GenreEntity

data class TvDetailsInfoEntity(
    val backdropImageUrl: String,
    val name: String,
    val rating: Float,
    val description: String,
    val genres: List<GenreEntity>
)