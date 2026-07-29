package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.TopRatedMovieEntity
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class TopRatedMovieEntityMapper @Inject constructor() : Mapper<TopRatedMovieEntity, Movie> {

    override fun map(input: TopRatedMovieEntity): Movie {
        return Movie(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = emptyList(),
            rate = input.rate
        )
    }
}
