package com.elhady.movies.core.data.repository.mappers.cash

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.GenresMoviesLocalDto
import com.elhady.movies.core.network.model.response.dto.GenreMovieRemoteDto
import javax.inject.Inject

class LocalGenresMovieMapper @Inject constructor() :
    Mapper<GenreMovieRemoteDto, GenresMoviesLocalDto> {

    override fun map(input: GenreMovieRemoteDto): GenresMoviesLocalDto {
        return GenresMoviesLocalDto(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
