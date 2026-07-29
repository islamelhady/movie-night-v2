package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.network.dto.common.GenreTvRemoteDto
import javax.inject.Inject

class LocalGenresTvMapper @Inject constructor() : Mapper<GenreTvRemoteDto, GenresTvsLocalDto> {
    override fun map(input: GenreTvRemoteDto): GenresTvsLocalDto {
        return GenresTvsLocalDto(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
