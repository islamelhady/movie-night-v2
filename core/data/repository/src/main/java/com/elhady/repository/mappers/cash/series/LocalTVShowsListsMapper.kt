package com.elhady.repository.mappers.cash.series

import com.elhady.repository.Constants
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto
import com.elhady.remote.response.dto.TVShowsRemoteDto
import com.elhady.repository.mappers.Mapper
import javax.inject.Inject

class LocalTVShowsListsMapper @Inject constructor() :
    Mapper<TVShowsRemoteDto, TVSeriesListsLocalDto> {
    override fun map(input: TVShowsRemoteDto): TVSeriesListsLocalDto {
        return TVSeriesListsLocalDto(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.posterPath)
        )
    }
}