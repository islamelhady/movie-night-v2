package com.elhady.movies.core.data.repository.mappers.cash.movie

import com.elhady.movies.core.data.local.database.dto.movie.TrendingMoviesLocalDto
import com.elhady.movies.core.data.remote.response.dto.MovieRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.GenreEntity
import javax.inject.Inject

class LocalTrendingMoviesMapper @Inject constructor() {
    fun map(input: MovieRemoteDto, genres: List<GenreEntity>): TrendingMoviesLocalDto {
        val genreIds = input.genreIds ?: emptyList()
        val genreNames = genreIds.mapNotNull { genreId ->
            genres.find { it.genreID == genreId }?.genreName ?: ""
        }

        return TrendingMoviesLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0,
            year = input.releaseDate ?: "",
            genres = genreNames
        )
    }
}
