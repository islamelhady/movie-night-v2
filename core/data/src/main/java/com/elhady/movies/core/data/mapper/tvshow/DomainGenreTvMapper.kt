package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.domain.model.GenreEntity
import javax.inject.Inject

class DomainGenreTvMapper @Inject constructor() : Mapper<GenresTvsLocalDto, GenreEntity> {
    override fun map(input: GenresTvsLocalDto): GenreEntity {
        return GenreEntity(
            genreID =  input.id,
            genreName = input.name
        )
    }
}
