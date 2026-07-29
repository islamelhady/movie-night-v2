package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class PopularMovieShowMoreDtoMapper @Inject constructor() {

    fun map(input: MovieDto, genreEntities: List<Genre>): Movie {
        return Movie(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.releaseDate ?: "",
            genreEntities = genreEntities.filter {
                it.genreID in (input.genreIds?.filterNotNull() ?: emptyList())
            },
            rate = input.voteAverage ?: 0.0
        )
    }
}
