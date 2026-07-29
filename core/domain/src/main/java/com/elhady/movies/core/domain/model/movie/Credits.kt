package com.elhady.movies.core.domain.model.movie

data class Credits(
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> =emptyList()
)
