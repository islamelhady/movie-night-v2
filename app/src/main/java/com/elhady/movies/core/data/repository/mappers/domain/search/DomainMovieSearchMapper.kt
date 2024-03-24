package com.elhady.movies.core.data.repository.mappers.domain.search

import com.elhady.movies.core.data.remote.response.dto.MovieRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.MovieEntity
import javax.inject.Inject

class DomainMovieSearchMapper @Inject constructor() {
    fun map(input: MovieRemoteDto, genres: List<GenreEntity>): MovieEntity {
        return MovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            year = input.releaseDate ?: "",
            genreEntities = filterGenres(genresIds = input.genreIds?.filterNotNull() ?: emptyList(), genresEntities = genres),
        )
    }

    fun map(input: List<MovieRemoteDto>, genres: List<GenreEntity>): List<MovieEntity> {
        return input.map {
            map(it, genres)
        }
    }

    private fun filterGenres(
        genresIds: List<Int>, // A list of genre IDs to filter by.
        genresEntities: List<GenreEntity> // The list of GenreEntity objects to be filtered.
    ): List<GenreEntity> {
        // Return a list of GenreEntity objects where each object's genreID is in the genresIds list.
        return genresEntities.filter { it.genreID in genresIds }
    }
}