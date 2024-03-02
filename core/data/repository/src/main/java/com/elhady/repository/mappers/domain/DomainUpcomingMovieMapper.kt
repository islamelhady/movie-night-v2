package com.elhady.repository.mappers.domain

import com.elhady.entities.MovieEntity
import com.elhady.local.Constants
import com.elhady.local.database.dto.movies.UpcomingMovieLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainUpcomingMovieMapper @Inject constructor()
    : Mapper<UpcomingMovieLocalDto, MovieEntity> {
    override fun map(input: UpcomingMovieLocalDto): MovieEntity {
        return MovieEntity(
            movieId = input.id,
            movieImage = (Constants.IMAGE_PATH + input.imageUrl),
            movieName = input.title,
            movieGenreEntities = emptyList(),
            movieRate = 0f,
            movieType = "",
            movieYear = ""
        )
    }
}