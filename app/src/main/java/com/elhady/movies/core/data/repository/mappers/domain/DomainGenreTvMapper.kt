package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.domain.entities.GenreEntity
import javax.inject.Inject

class DomainGenreTvMapper @Inject constructor() : Mapper<GenresTvsLocalDto, GenreEntity> {
    override fun map(input: GenresTvsLocalDto): GenreEntity {
        return GenreEntity(
            genreID =  input.id,
            genreName = input.name
        )
    }
}