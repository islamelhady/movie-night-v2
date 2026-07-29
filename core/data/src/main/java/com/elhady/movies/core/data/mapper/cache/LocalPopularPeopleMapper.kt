package com.elhady.movies.core.data.mapper.cache

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.PopularPeopleLocalDto
import com.elhady.movies.core.network.model.response.dto.PeopleRemoteDto
import com.elhady.movies.core.data.BuildConfig
import javax.inject.Inject

class LocalPopularPeopleMapper @Inject constructor() : Mapper<PeopleRemoteDto, PopularPeopleLocalDto> {
    override fun map(input: PeopleRemoteDto): PopularPeopleLocalDto {
        return PopularPeopleLocalDto(
            id = input.id ?: 0,
            imagerUrl = BuildConfig.IMAGE_BASE_PATH + input.profilePath,
            name = input.name ?: "",
            popularity = input.popularity ?: 0.0
        )
    }
}
