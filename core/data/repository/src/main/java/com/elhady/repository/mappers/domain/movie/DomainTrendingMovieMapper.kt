package com.elhady.repository.mappers.domain.movie

import com.elhady.entities.MovieEntity
import com.elhady.local.database.dto.movies.TrendingMovieLocalDto
import com.elhady.movies.domain.mappers.Mapper
import javax.inject.Inject

class DomainTrendingMovieMapper @Inject constructor() : Mapper<TrendingMovieLocalDto, MovieEntity> {
    override fun map(input: TrendingMovieLocalDto): MovieEntity {
        return MovieEntity(
            movieId = input.id,
            movieName = input.name,
            movieImage = input.imageUrl,
            movieType = "",
            movieYear = "",
            movieRate = 0.0,
            movieGenreEntities = emptyList()
        )
    }
}