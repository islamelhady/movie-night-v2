package com.elhady.movies.core.data.mappers.domain.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.model.response.dto.MovieRemoteDto
import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.model.MovieEntity
import javax.inject.Inject

class DomainMovieMapper @Inject constructor() {
    fun map(input: MovieRemoteDto, genres: List<GenreEntity>, mediaType:String ="movie"): MovieEntity {
        return MovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.releaseDate ?: "",
            genreEntities = genres,
            mediaType = mediaType,
        )
    }

    fun map(input: List<MovieRemoteDto>, genres: List<GenreEntity>): List<MovieEntity> {
        return input.map {
            map(it, genres)
        }
    }
}
