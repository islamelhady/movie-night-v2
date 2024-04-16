package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.GenresMoviesLocalDto
import com.elhady.movies.core.domain.entities.GenreEntity
import javax.inject.Inject

class DomainGenreMapper @Inject constructor() : Mapper<GenresMoviesLocalDto, GenreEntity> {
    override fun map(input: GenresMoviesLocalDto): GenreEntity {
        return GenreEntity(
            genreID =  input.id,
            genreName = input.name
        )
    }
}