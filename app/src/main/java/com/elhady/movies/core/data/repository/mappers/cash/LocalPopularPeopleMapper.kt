package com.elhady.movies.core.data.repository.mappers.cash

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.PopularPeopleLocalDto
import com.elhady.movies.core.data.remote.response.dto.PeopleRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import javax.inject.Inject

class LocalPopularPeopleMapper @Inject constructor() :
    Mapper<PeopleRemoteDto, PopularPeopleLocalDto> {
    override fun map(input: PeopleRemoteDto): PopularPeopleLocalDto {
        return PopularPeopleLocalDto(
            id = input.id ?: 0,
            imagerUrl = IMAGE_BASE_PATH + input.profilePath,
            name = input.name ?: "",
            popularity = input.popularity ?: 0.0
        )
    }
}