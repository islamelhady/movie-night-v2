package com.elhady.movies.domain.mappers.series

import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Media
import javax.inject.Inject

class AiringTodaySeriesMapper @Inject constructor(): Mapper<AiringTodaySeriesEntity, Media> {
    override fun map(input: AiringTodaySeriesEntity): Media {
        return Media(
            mediaID = input.id,
            mediaName = input.name,
            mediaImage = input.imageUrl,
            mediaType = "",
            mediaRate = 0f,
            mediaDate = ""
        )
    }
}