package com.elhady.movies.core.data.repository.mappers.domain

import com.elhady.movies.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.model.response.dto.PeopleDetailsResponse
import com.elhady.movies.core.common.domain.entities.PeopleDetailsEntity
import javax.inject.Inject

class DomainPeopleDetailsMapper @Inject constructor() :
    Mapper<PeopleDetailsResponse, PeopleDetailsEntity> {
    override fun map(input: PeopleDetailsResponse): PeopleDetailsEntity {
        return PeopleDetailsEntity(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (BuildConfig.IMAGE_BASE_PATH + input.profilePath),
            placeOfBirth = input.placeOfBirth ?: "",
            gender = input.gender.toString(),
            acting = input.knownForDepartment.toString(),
            num_movies = "",
            biography = input.biography ?:""
        )
    }
}
