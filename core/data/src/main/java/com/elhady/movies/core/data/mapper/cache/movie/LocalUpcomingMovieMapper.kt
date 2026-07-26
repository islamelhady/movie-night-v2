package com.elhady.movies.core.data.mapper.cache.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.dto.movie.UpcomingMovieLocalDto
import com.elhady.movies.core.network.model.response.dto.MovieRemoteDto
import com.elhady.movies.core.domain.model.GenreEntity
import javax.inject.Inject

class LocalUpcomingMovieMapper @Inject constructor() {
    fun map(input: MovieRemoteDto, genres: List<GenreEntity>): UpcomingMovieLocalDto {

        val genreIds = input.genreIds ?: emptyList()
        val genreNames = genreIds.mapNotNull { genreId ->
            genres.find { it.genreID == genreId }?.genreName ?: ""
        }

        return UpcomingMovieLocalDto(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0,
            genres = genreNames
        )
    }
}
