package com.elhady.movies.data.local.mappers.series

import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class OnTheAirSeriesMapper @Inject constructor() : Mapper<TVShowDto, OnTheAirSeriesEntity> {
    override fun map(input: TVShowDto): OnTheAirSeriesEntity {
        return OnTheAirSeriesEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}