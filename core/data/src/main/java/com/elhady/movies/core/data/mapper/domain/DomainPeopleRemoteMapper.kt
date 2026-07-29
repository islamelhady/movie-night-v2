package com.elhady.movies.core.data.mapper.domain

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.PeopleRemoteDto
import com.elhady.movies.core.domain.model.PeopleEntity
import javax.inject.Inject

class DomainPeopleRemoteMapper @Inject constructor() : Mapper<PeopleRemoteDto, PeopleEntity> {
    override fun map(input: PeopleRemoteDto): PeopleEntity {
        return PeopleEntity(
            id =  input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.profilePath
        )
    }
}
