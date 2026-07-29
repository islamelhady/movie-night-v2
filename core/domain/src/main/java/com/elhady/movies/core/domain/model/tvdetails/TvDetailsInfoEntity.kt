package com.elhady.movies.core.domain.model.tvdetails

import com.elhady.movies.core.domain.model.GenreEntity

data class TvDetailsInfoEntity(
    val backdropImageUrl: String,
    val name: String,
    val rating: Float,
    val description: String,
    val genres: List<GenreEntity>
)
