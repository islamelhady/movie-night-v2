package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.PopularPeopleLocalDto
import com.elhady.movies.core.domain.entities.PeopleEntity
import javax.inject.Inject

class DomainPeopleMapper @Inject constructor() : Mapper<PopularPeopleLocalDto, PeopleEntity> {
    override fun map(input: PopularPeopleLocalDto): PeopleEntity {
        return PeopleEntity(
            id =  input.id,
            name = input.name,
            imageUrl = input.imagerUrl,
            popularity = input.popularity
        )
    }
}