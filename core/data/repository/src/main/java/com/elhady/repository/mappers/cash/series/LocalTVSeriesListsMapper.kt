package com.elhady.repository.mappers.cash.series

import com.elhady.local.Constants
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto
import com.elhady.local.mappers.Mapper
import com.elhady.remote.response.dto.TVShowsRemoteDto
import javax.inject.Inject

class LocalTVSeriesListsMapper @Inject constructor() : Mapper<TVShowsRemoteDto, TVSeriesListsLocalDto> {
    override fun map(input: TVShowsRemoteDto): TVSeriesListsLocalDto {
        return TVSeriesListsLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}