package com.elhady.repository.mappers.domain.series

import com.elhady.entities.TvShowEntity
import com.elhady.local.database.dto.series.OnTheAirSeriesLocalDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class DomainOnTheAirTVShowMapper @Inject constructor() : Mapper<OnTheAirSeriesLocalDto, TvShowEntity> {
    override fun map(input: OnTheAirSeriesLocalDto): TvShowEntity {
        return TvShowEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = input.imageUrl,
            rate = 0.0
        )
    }
}