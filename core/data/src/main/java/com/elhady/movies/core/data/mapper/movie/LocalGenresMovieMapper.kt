package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.genre.GenresMoviesEntity
import com.elhady.movies.core.network.dto.common.GenreMovieDto
import javax.inject.Inject

class LocalGenresMovieMapper @Inject constructor() : Mapper<GenreMovieDto, GenresMoviesEntity> {
    override fun map(input: GenreMovieDto): GenresMoviesEntity {
        return GenresMoviesEntity(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
