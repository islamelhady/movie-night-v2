package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class OnTheAirSeriesMapper @Inject constructor() : Mapper<OnTheAirSeriesEntity, Media> {
    override fun map(input: OnTheAirSeriesEntity): Media {
        return Media(
            mediaID = input.id,
            mediaDate = "",
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaRate = 0f,
            mediaType = ""
        )
    }
}