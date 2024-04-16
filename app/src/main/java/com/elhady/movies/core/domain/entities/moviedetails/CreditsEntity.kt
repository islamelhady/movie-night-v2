package com.elhady.movies.core.domain.entities.moviedetails

data class CreditsEntity(
    val cast: List<CastEntity> = emptyList(),
    val crew: List<CrewEntity> =emptyList()
)