package com.elhady.movies.core.data.repository.mappers.cash

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.data.remote.response.dto.GenreTVRemoteDto
import javax.inject.Inject

class LocalGenresTvMapper @Inject constructor() :
    Mapper<GenreTVRemoteDto, GenresTvsLocalDto> {

    override fun map(input: GenreTVRemoteDto): GenresTvsLocalDto {
        return GenresTvsLocalDto(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}