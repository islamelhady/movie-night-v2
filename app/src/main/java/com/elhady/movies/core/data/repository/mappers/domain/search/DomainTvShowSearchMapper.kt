package com.elhady.movies.core.data.repository.mappers.domain.search

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.data.remote.response.dto.TvRemoteDto
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.TvEntity
import javax.inject.Inject

class DomainTvShowSearchMapper @Inject constructor() {
    fun map(input: TvRemoteDto, genres: List<GenreEntity>): TvEntity {
        return TvEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.firstAirDate ?: "",
            genreEntities = filterGenres(genresIds = input.genreIds?.filterNotNull() ?: emptyList(), genresEntities = genres)
        )
    }

    fun map(input: List<TvRemoteDto>, genres: List<GenreEntity>): List<TvEntity> {
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