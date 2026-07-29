package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.genre.GenresMoviesEntity
import com.elhady.movies.core.domain.model.common.Genre
import javax.inject.Inject

class GenreEntityMapper @Inject constructor() : Mapper<GenresMoviesEntity, Genre> {
        override fun map(input: GenresMoviesEntity): Genre {
        return Genre(
            genreID =  input.id,
            genreName = input.name
        )
    }
}
