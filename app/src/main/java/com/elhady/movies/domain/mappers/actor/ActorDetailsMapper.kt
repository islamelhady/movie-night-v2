package com.elhady.movies.domain.mappers.actor

import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.ActorDetails
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class ActorDetailsMapper @Inject constructor(): Mapper<PersonDto, ActorDetails> {
    override fun map(input: PersonDto): ActorDetails {
        return ActorDetails(
            id = input.id ?: 0,
            name = input.name ?: "",
            imageUrl = (Constants.IMAGE_PATH + input.profilePath)
        )
    }
}