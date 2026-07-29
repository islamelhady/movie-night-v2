package com.elhady.movies.core.domain.model.tvshow

import com.elhady.movies.core.domain.model.common.GenreEntity

data class TVShowsEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<GenreEntity>,
    val rate: Double,
    val year: String = ""
)
