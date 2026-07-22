package com.elhady.movies.core.data.repository.mappers.domain.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.TrendingMoviesLocalDto
import com.elhady.movies.core.common.domain.entities.GenreEntity
import com.elhady.movies.core.common.domain.entities.MovieEntity
import javax.inject.Inject


class DomainTrendingMoviesMapper @Inject constructor() :
    Mapper<TrendingMoviesLocalDto, MovieEntity> {

    override fun map(input: TrendingMoviesLocalDto): MovieEntity {
        return MovieEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            genreEntities = input.genres.map { GenreEntity(genreName = it) },
            rate = input.rate,
            year = input.year
        )
    }
}
