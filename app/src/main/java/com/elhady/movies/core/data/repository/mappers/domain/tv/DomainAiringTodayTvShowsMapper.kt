package com.elhady.movies.core.data.repository.mappers.domain.tv

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.TVShowsRemoteDto
import com.elhady.movies.core.domain.entities.TVShowsEntity
import javax.inject.Inject

class DomainAiringTodayTvShowsMapper @Inject constructor() :
    Mapper<TVShowsRemoteDto, TVShowsEntity> {

    override fun map(input: TVShowsRemoteDto): TVShowsEntity {
        return TVShowsEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath ,
            genreEntities = emptyList(),
            rate = input.voteAverage ?: 0.0,
            year = input.firstAirDate ?: "Unknown"
        )
    }
}