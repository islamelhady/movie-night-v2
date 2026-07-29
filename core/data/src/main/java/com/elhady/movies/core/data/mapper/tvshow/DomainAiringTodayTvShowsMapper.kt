package com.elhady.movies.core.data.mapper.tvshow

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.tvshow.TVShowsDto
import com.elhady.movies.core.domain.model.tvshow.TVShowsEntity
import javax.inject.Inject

class DomainAiringTodayTvShowsMapper @Inject constructor() :
    Mapper<TVShowsDto, TVShowsEntity> {

    override fun map(input: TVShowsDto): TVShowsEntity {
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
