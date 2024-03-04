package com.elhady.repository.mappers.domain.movie

import com.elhady.entities.MovieEntity
import com.elhady.local.database.dto.movies.TopRatedMovieLocalDto
import com.elhady.movies.domain.mappers.Mapper
import javax.inject.Inject

class DomainTopRatedMovieMapper @Inject constructor() : Mapper<TopRatedMovieLocalDto, MovieEntity> {
    override fun map(input: TopRatedMovieLocalDto): MovieEntity {
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