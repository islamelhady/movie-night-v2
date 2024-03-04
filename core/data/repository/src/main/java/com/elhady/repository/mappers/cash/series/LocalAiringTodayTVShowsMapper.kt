package com.elhady.repository.mappers.cash.series

import com.elhady.local.Constants
import com.elhady.local.database.dto.series.AiringTodaySeriesLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.TVShowsRemoteDto
import javax.inject.Inject

class LocalAiringTodayTVShowsMapper @Inject constructor() : Mapper<TVShowsRemoteDto, AiringTodaySeriesLocalDto> {
    override fun map(input: TVShowsRemoteDto): AiringTodaySeriesLocalDto {
        return AiringTodaySeriesLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}