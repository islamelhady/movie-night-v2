package com.elhady.movies.core.common.domain.entities.tvdetails

import com.elhady.movies.core.common.domain.entities.GenreEntity

data class TvDetailsInfoEntity(
    val backdropImageUrl: String,
    val name: String,
    val rating: Float,
    val description: String,
    val genres: List<GenreEntity>
)
