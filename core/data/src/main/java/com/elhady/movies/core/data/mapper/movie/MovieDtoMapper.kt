package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class MovieDtoMapper @Inject constructor() {
    fun map(input: MovieDto, genres: List<Genre>, mediaType: MediaType = MediaType.MOVIE): Movie {
        return Movie(
            id = input.id ?: 0,
            title = input.title ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.releaseDate ?: "",
            genreEntities = genres,
            mediaType = mediaType,
        )
    }

    fun map(input: List<MovieDto>, genres: List<Genre>): List<Movie> {
        return input.map {
            map(it, genres)
        }
    }
}
