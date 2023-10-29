package com.elhady.movies.domain.mappers.actor

import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Actor
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class ActorDtoMapper @Inject constructor(): Mapper<PersonDto, Actor> {
    override fun map(input: PersonDto): Actor {
        return Actor(
            id = input.id ?: 0,
            name = input.name ?: "unknown",
            imageUrl = (Constants.IMAGE_PATH + input.profilePath)
        )
    }
}