package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.entity.movie.TrendingMoviesEntity
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.domain.model.common.Genre
import javax.inject.Inject

class TrendingMoviesDtoToEntityMapper @Inject constructor() {
    fun map(input: MovieDto, genres: List<Genre>): TrendingMoviesEntity {
        val genreIds = input.genreIds ?: emptyList()
        val genreNames = genreIds.mapNotNull { genreId ->
            genres.find { it.genreID == genreId }?.genreName ?: ""
        }

        return TrendingMoviesEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0,
            year = input.releaseDate ?: "",
            genres = genreNames
        )
    }
}
