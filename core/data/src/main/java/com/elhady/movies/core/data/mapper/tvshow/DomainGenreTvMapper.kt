package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.genre.GenresTvEntity
import com.elhady.movies.core.domain.model.common.GenreEntity
import javax.inject.Inject

class DomainGenreTvMapper @Inject constructor() : Mapper<GenresTvEntity, GenreEntity> {
    override fun map(input: GenresTvEntity): GenreEntity {
        return GenreEntity(
            genreID =  input.id,
            genreName = input.name
        )
    }
}
