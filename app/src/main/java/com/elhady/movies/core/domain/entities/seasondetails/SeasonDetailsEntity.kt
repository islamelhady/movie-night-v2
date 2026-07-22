package com.elhady.movies.core.common.domain.entities.seasondetails


data class SeasonDetailsEntity(
    val id: Int,
    val name : String,
    val overview : String,
    val episodes : List<EpisodeEntity>
)
