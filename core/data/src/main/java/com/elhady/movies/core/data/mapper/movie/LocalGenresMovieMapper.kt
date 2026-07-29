package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.GenresMoviesLocalDto
import com.elhady.movies.core.network.dto.common.GenreMovieDto
import javax.inject.Inject

class LocalGenresMovieMapper @Inject constructor() : Mapper<GenreMovieDto, GenresMoviesLocalDto> {
    override fun map(input: GenreMovieDto): GenresMoviesLocalDto {
        return GenresMoviesLocalDto(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
