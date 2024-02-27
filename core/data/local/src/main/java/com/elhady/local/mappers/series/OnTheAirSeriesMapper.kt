package com.elhady.local.mappers.series

import com.elhady.local.Constants
import com.elhady.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.series.SeriesDto
import javax.inject.Inject

class OnTheAirSeriesMapper @Inject constructor() : Mapper<SeriesDto, OnTheAirSeriesEntity> {
    override fun map(input: SeriesDto): OnTheAirSeriesEntity {
        return OnTheAirSeriesEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}