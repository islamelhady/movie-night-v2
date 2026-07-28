package com.elhady.movies.core.data.mapper.search

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.model.response.dto.TvRemoteDto
import com.elhady.movies.core.domain.model.GenreEntity
import com.elhady.movies.core.domain.model.TvEntity
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
        genresIds: List<Int>,
        genresEntities: List<GenreEntity>
    ): List<GenreEntity> {
        return genresEntities.filter { it.genreID in genresIds }
    }
}
