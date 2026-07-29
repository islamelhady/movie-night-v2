package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.GenresMoviesEntity
import com.elhady.movies.core.domain.model.common.GenreEntity
import javax.inject.Inject

class DomainGenreMapper @Inject constructor() : Mapper<GenresMoviesEntity, GenreEntity> {
    override fun map(input: GenresMoviesEntity): GenreEntity {
        return GenreEntity(
            genreID =  input.id,
            genreName = input.name
        )
    }
}
