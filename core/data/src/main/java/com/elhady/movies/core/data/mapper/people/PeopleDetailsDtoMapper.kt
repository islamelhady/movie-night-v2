package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.PeopleDetailsDto
import com.elhady.movies.core.domain.model.people.PeopleDetails
import javax.inject.Inject

class PeopleDetailsDtoMapper @Inject constructor() :
    Mapper<PeopleDetailsDto, PeopleDetails> {
    override fun map(input: PeopleDetailsDto): PeopleDetails {
        return PeopleDetails(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (BuildConfig.IMAGE_BASE_PATH + input.profilePath),
            placeOfBirth = input.placeOfBirth ?: "",
            gender = input.gender.toString(),
            acting = input.knownForDepartment.toString(),
            numMovies = "",
            biography = input.biography ?:""
        )
    }
}
