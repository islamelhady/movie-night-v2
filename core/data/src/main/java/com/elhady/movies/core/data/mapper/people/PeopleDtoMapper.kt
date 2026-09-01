package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.people.PeopleDto
import com.elhady.movies.core.domain.model.people.People
import javax.inject.Inject

class PeopleDtoMapper @Inject constructor() : Mapper<PeopleDto, People> {

    override fun map(input: List<PeopleDto>): List<People> {
        return input.map(::map)
    }

    override fun map(input: PeopleDto): People {
        return People(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.profilePath,
            popularity = input.popularity ?: 0.0
        )
    }
}
