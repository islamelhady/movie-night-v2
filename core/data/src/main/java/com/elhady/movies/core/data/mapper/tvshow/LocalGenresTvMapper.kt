package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.genre.GenresTvEntity
import com.elhady.movies.core.network.dto.common.GenreTvDto
import javax.inject.Inject

class LocalGenresTvMapper @Inject constructor() : Mapper<GenreTvDto, GenresTvEntity> {
    override fun map(input: GenreTvDto): GenresTvEntity {
        return GenresTvEntity(
            id = input.id ?: 0,
            name = input.name ?: ""
        )
    }
}
