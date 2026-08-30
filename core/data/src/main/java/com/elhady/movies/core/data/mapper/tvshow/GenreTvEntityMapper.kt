package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.database.entity.genre.GenresTvEntity
import com.elhady.movies.core.domain.model.common.Genre
import javax.inject.Inject

class GenreTvEntityMapper @Inject constructor() : Mapper<GenresTvEntity, Genre> {
    override fun map(input: GenresTvEntity): Genre {
        return Genre(
            genreID =  input.id,
            genreName = input.name
        )
    }
}
