package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.network.dto.common.GenreTvDto
import javax.inject.Inject

class LocalGenresTvMapper @Inject constructor() : Mapper<GenreTvDto, GenresTvsLocalDto> {
    override fun map(input: GenreTvDto): GenresTvsLocalDto {
        return GenresTvsLocalDto(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
