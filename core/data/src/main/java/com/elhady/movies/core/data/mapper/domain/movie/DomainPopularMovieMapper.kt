package com.elhady.movies.core.data.mapper.domain.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.PopularMovieLocalDto
import com.elhady.movies.core.domain.model.MovieEntity
import javax.inject.Inject

class DomainPopularMovieMapper @Inject constructor():
    Mapper<PopularMovieLocalDto, MovieEntity> {

    override fun map(input: PopularMovieLocalDto): MovieEntity {
        return MovieEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = emptyList(),
            rate = input.rate
        )
    }
}
