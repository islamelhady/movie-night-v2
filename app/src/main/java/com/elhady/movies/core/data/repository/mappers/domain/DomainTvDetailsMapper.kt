package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TvDetailsRemoteDto
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.tvdetails.TvDetailsInfoEntity
import javax.inject.Inject

class DomainTvDetailsMapper @Inject constructor() :
    Mapper<TvDetailsRemoteDto, TvDetailsInfoEntity> {
    override fun map(input: TvDetailsRemoteDto): TvDetailsInfoEntity {
        return TvDetailsInfoEntity(
            backdropImageUrl = BuildConfig.IMAGE_BASE_PATH + input.backdropPath,
            name = input.name ?: "",
            rating = input.voteAverage?.toFloat()?.times(0.5f) ?: 0.0f,
            description = input.overview ?: "",
            genres = mapGenreToEntity(input.genres)
        )
    }

    private fun mapGenreToEntity(genreRemoteDto: List<TvDetailsRemoteDto.Genre?>?): List<GenreEntity> {
        return genreRemoteDto?.map {
            GenreEntity(
                genreID = it?.id ?: 0,
                genreName = it?.name ?: ""
            )
        } ?: emptyList()
    }
}

