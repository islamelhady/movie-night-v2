package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.domain.model.common.GenreEntity
import com.elhady.movies.core.domain.model.movie.MovieEntity
import javax.inject.Inject

class DomainMovieMapper @Inject constructor() {
    fun map(input: MovieDto, genres: List<GenreEntity>, mediaType:String ="movie"): MovieEntity {
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

    fun map(input: List<MovieDto>, genres: List<GenreEntity>): List<MovieEntity> {
        return input.map {
            map(it, genres)
        }
    }
}
