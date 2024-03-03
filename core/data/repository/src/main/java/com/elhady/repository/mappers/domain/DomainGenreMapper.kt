package com.elhady.repository.mappers.domain

import com.elhady.entities.GenreEntity
import com.elhady.local.database.dto.GenresMoviesLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainGenreMapper @Inject constructor() : Mapper<GenresMoviesLocalDto, GenreEntity> {
    override fun map(input: GenresMoviesLocalDto): GenreEntity {
        return GenreEntity(
            genreId =  input.id,
            genreName = input.name
        )
    }
}