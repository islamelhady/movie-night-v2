package com.elhady.movies.core.data.mapper.domain.tv

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.TVShowsRemoteDto
import com.elhady.movies.core.domain.model.TVShowsEntity
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
