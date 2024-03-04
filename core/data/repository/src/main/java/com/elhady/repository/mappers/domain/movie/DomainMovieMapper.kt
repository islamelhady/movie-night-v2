package com.elhady.repository.mappers.domain.movie

import com.elhady.entities.GenreEntity
import com.elhady.entities.MovieEntity
import com.elhady.local.Constants
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class DomainMovieMapper @Inject constructor() {
    fun map(input: MovieRemoteDto, genreEntities: List<GenreEntity>): MovieEntity {
        return MovieEntity(
            movieId = input.id ?: 0,
            movieName = input.title ?: "",
            movieImage = Constants.IMAGE_PATH + input.posterPath,
            movieYear = input.releaseDate ?: "",
            movieGenreEntities = genreEntities.filter {
                it.genreId in (input.genreIds?.filterNotNull() ?: emptyList())
            },
            movieRate = input.voteAverage ?: 0.0
        )
    }
}