package com.elhady.movies.core.domain.model.movie

data class CreditsEntity(
    val cast: List<CastEntity> = emptyList(),
    val crew: List<CrewEntity> =emptyList()
)
