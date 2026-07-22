package com.elhady.movies.core.common.domain.entities.moviedetails

data class CreditsEntity(
    val cast: List<CastEntity> = emptyList(),
    val crew: List<CrewEntity> =emptyList()
)
