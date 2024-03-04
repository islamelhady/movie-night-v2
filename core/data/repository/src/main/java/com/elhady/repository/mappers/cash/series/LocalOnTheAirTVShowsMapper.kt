package com.elhady.repository.mappers.cash.series

import com.elhady.local.Constants
import com.elhady.local.database.dto.series.OnTheAirSeriesLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.TVShowsRemoteDto
import javax.inject.Inject

class LocalOnTheAirTVShowsMapper @Inject constructor() : Mapper<TVShowsRemoteDto, OnTheAirSeriesLocalDto> {
    override fun map(input: TVShowsRemoteDto): OnTheAirSeriesLocalDto {
        return OnTheAirSeriesLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}