package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.people.PopularPeopleEntity
import com.elhady.movies.core.domain.model.people.People
import javax.inject.Inject

class PopularPeopleEntityMapper @Inject constructor() : Mapper<PopularPeopleEntity, People> {
    override fun map(input: PopularPeopleEntity): People {
        return People(
            id =  input.id,
            name = input.name,
            imageUrl = input.imagerUrl,
            popularity = input.popularity
        )
    }
}
