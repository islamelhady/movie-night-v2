package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.GenresMoviesLocalDto
import com.elhady.movies.core.domain.model.GenreEntity
import javax.inject.Inject

class DomainGenreMapper @Inject constructor() : Mapper<GenresMoviesLocalDto, GenreEntity> {
    override fun map(input: GenresMoviesLocalDto): GenreEntity {
        return GenreEntity(
            genreID =  input.id,
            genreName = input.name
        )
    }
}
