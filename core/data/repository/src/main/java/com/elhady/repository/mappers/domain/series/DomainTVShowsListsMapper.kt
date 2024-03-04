package com.elhady.repository.mappers.domain.series

import com.elhady.entities.TvShowEntity
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainTVShowsListsMapper @Inject constructor() : Mapper<TVSeriesListsLocalDto, TvShowEntity> {
    override fun map(input: TVSeriesListsLocalDto): TvShowEntity {
        return TvShowEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = input.imageUrl,
            rate = 0.0
        )
    }
}