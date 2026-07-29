package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.PopularPeopleEntity
import com.elhady.movies.core.domain.model.people.PeopleEntity
import javax.inject.Inject

class DomainPeopleMapper @Inject constructor() : Mapper<PopularPeopleEntity, PeopleEntity> {
    override fun map(input: PopularPeopleEntity): PeopleEntity {
        return PeopleEntity(
            id =  input.id,
            name = input.name,
            imageUrl = input.imagerUrl,
            popularity = input.popularity
        )
    }
}
