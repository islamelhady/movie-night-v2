package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.database.entity.movie.PopularMovieEntity
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class PopularMovieEntityMapper @Inject constructor():
    Mapper<PopularMovieEntity, Movie> {

    override fun map(input: PopularMovieEntity): Movie {
        return Movie(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = emptyList(),
            rate = input.rate
        )
    }
}
