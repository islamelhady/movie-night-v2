package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.TopRatedMovieEntity
import com.elhady.movies.core.domain.model.movie.MovieEntity
import javax.inject.Inject

class DomainTopRatedMovieMapper @Inject constructor() : Mapper<TopRatedMovieEntity, MovieEntity> {

    override fun map(input: TopRatedMovieEntity): MovieEntity {
        return MovieEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = emptyList(),
            rate = input.rate
        )
    }
}
