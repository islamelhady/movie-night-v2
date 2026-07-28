package com.elhady.movies.core.domain.model.tvshow

import com.elhady.movies.core.domain.model.common.GenreEntity

data class TvDetailsInfoEntity(
    val backdropImageUrl: String,
    val name: String,
    val rating: Float,
    val description: String,
    val genres: List<GenreEntity>
)
