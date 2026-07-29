package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.PeopleDto
import com.elhady.movies.core.domain.model.people.PeopleEntity
import javax.inject.Inject

class DomainPeopleRemoteMapper @Inject constructor() : Mapper<PeopleDto, PeopleEntity> {

    override fun map(input: List<PeopleDto>): List<PeopleEntity> {
        return input.map(::map)
    }

    override fun map(input: PeopleDto): PeopleEntity {
        return PeopleEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.profilePath,
            popularity = input.popularity ?: 0.0
        )
    }
}
