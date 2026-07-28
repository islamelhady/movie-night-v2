package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.network.model.response.dto.GenreTVRemoteDto
import javax.inject.Inject

class LocalGenresTvMapper @Inject constructor() : Mapper<GenreTVRemoteDto, GenresTvsLocalDto> {
    override fun map(input: GenreTVRemoteDto): GenresTvsLocalDto {
        return GenresTvsLocalDto(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
