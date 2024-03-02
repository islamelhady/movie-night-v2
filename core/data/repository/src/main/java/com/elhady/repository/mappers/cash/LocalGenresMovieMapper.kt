package com.elhady.repository.mappers.cash

import com.elhady.local.database.dto.GenresMoviesLocalDto
import com.elhady.remote.response.genre.GenreMovieRemoteDto
import com.elhady.repository.mappers.Mapper
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