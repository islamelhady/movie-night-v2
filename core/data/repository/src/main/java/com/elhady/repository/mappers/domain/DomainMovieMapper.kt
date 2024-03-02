package com.elhady.repository.mappers.domain

import com.elhady.entities.GenreEntity
import com.elhady.entities.MovieEntity
import com.elhady.local.Constants
import com.elhady.remote.response.dto.MovieRemoteDto
import javax.inject.Inject

class DomainMovieMapper @Inject constructor() {
    fun map(input: MovieRemoteDto, genre: List<GenreEntity>, mediaType: String = "movie"): MovieEntity {
        return MovieEntity(
            movieId = input.id ?: 0,
            movieName = input.title ?: "",
            movieRate = input.voteAverage ?: 0.0,
            movieImage = Constants.IMAGE_PATH + input.backdropPath,
            movieGenreEntities = genre,
            movieYear = input.releaseDate ?: "",
            movieType = mediaType
        )
    }

    private fun map(input: List<MovieRemoteDto>, genres: List<GenreEntity>): List<MovieEntity> {
        return input.map {
            map(it, genres)
        }
    }
}