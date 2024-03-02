package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.domain.models.Genre
import javax.inject.Inject

class GenreMapper @Inject constructor() : Mapper<GenreDto, Genre> {
    override fun map(input: GenreDto): Genre {
        return Genre(
            id = input.id ?: 0,
            name = input.name ?: "unknown"
        )
    }
}