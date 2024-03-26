package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.remote.response.dto.PeopleRemoteDto
import com.elhady.movies.core.domain.entities.PeopleEntity
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