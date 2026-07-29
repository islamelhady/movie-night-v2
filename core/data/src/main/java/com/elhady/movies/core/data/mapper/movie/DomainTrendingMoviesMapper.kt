package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.TrendingMoviesEntity
import com.elhady.movies.core.domain.model.common.GenreEntity
import com.elhady.movies.core.domain.model.movie.MovieEntity
import javax.inject.Inject


class DomainTrendingMoviesMapper @Inject constructor() :
    Mapper<TrendingMoviesEntity, MovieEntity> {

    override fun map(input: TrendingMoviesEntity): MovieEntity {
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
