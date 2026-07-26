package com.elhady.movies.core.domain.model.seasondetails


data class SeasonDetailsEntity(
    val id: Int,
    val name : String,
    val overview : String,
    val episodes : List<EpisodeEntity>
)
