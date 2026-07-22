package com.elhady.movies.core.data.mappers.domain.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.UpcomingMovieLocalDto
import com.elhady.movies.core.common.domain.entities.GenreEntity
import com.elhady.movies.core.common.domain.entities.MovieEntity
import javax.inject.Inject

class DomainUpcomingMovieMapper @Inject constructor(): Mapper<UpcomingMovieLocalDto, MovieEntity> {

    override fun map(input: UpcomingMovieLocalDto): MovieEntity {
        return MovieEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = input.genres.map { GenreEntity(genreName = it) },
            rate = input.rate
        )
    }
}
