package com.elhady.local.mappers.series

import com.elhady.local.Constants
import com.elhady.local.database.dto.series.AiringTodaySeriesLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.series.SeriesDto
import javax.inject.Inject

class AiringTodaySeriesMapper @Inject constructor() : Mapper<SeriesDto, AiringTodaySeriesLocalDto> {
    override fun map(input: SeriesDto): AiringTodaySeriesLocalDto {
        return AiringTodaySeriesLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}