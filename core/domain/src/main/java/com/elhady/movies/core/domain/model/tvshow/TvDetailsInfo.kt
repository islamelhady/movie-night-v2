package com.elhady.movies.core.domain.model.tvshow

import com.elhady.movies.core.domain.model.common.Genre

data class TvDetailsInfo(
    val backdropImageUrl: String,
    val name: String,
    val rating: Float,
    val description: String,
    val genres: List<Genre>
)
