package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.UpcomingMovieEntity
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class UpcomingMovieEntityMapper @Inject constructor(): Mapper<UpcomingMovieEntity, Movie> {

    override fun map(input: UpcomingMovieEntity): Movie {
        return Movie(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = input.genres.map { Genre(genreName = it) },
            rate = input.rate
        )
    }
}
