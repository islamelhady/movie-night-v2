package com.elhady.movies.data.local.mappers.series

import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
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