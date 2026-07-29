package com.elhady.movies.core.data.mapper.search

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class MovieSearchDtoMapper @Inject constructor() {
    fun map(input: MovieDto, genres: List<Genre>): Movie {
        return Movie(
            id = input.id ?: 0,
            title = input.title ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.releaseDate ?: "",
            genreEntities = filterGenres(genresIds = input.genreIds?.filterNotNull() ?: emptyList(), genresEntities = genres),
        )
    }

    fun map(input: List<MovieDto>, genres: List<Genre>): List<Movie> {
        return input.map {
            map(it, genres)
        }
    }

    private fun filterGenres(
        genresIds: List<Int>,
        genresEntities: List<Genre>
    ): List<Genre> {
        return genresEntities.filter { it.genreID in genresIds }
    }
}
