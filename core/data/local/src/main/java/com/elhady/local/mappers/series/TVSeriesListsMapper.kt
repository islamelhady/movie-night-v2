package com.elhady.local.mappers.series

import com.elhady.local.Constants
import com.elhady.local.database.entity.series.TVSeriesListsEntity
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.series.SeriesDto
import javax.inject.Inject

class TVSeriesListsMapper @Inject constructor() : Mapper<SeriesDto, TVSeriesListsEntity> {
    override fun map(input: SeriesDto): TVSeriesListsEntity {
        return TVSeriesListsEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}