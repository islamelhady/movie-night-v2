package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.database.entity.movie.UpcomingMovieEntity
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.domain.model.common.GenreEntity
import javax.inject.Inject

class LocalUpcomingMovieMapper @Inject constructor() {
    fun map(input: MovieDto, genres: List<GenreEntity>): UpcomingMovieEntity {

        val genreIds = input.genreIds ?: emptyList()
        val genreNames = genreIds.mapNotNull { genreId ->
            genres.find { it.genreID == genreId }?.genreName ?: ""
        }

        return UpcomingMovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0,
            genres = genreNames
        )
    }
}
